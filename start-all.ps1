<#
.SYNOPSIS
Smart MES System One-Click Start Script

.DESCRIPTION
This script starts both backend (Spring Boot) and frontend (Vue) services for Smart MES System,
with environment checking, service startup and status verification.
#>

param(
    [string]$BackendDir = "smartMesProj",
    [string]$FrontendDir = "front-end",
    [int]$BackendPort = 8081,
    [int]$FrontendPort = 5173,
    [int]$WaitInterval = 3,
    [int]$MaxRetries = 30
)

$ErrorActionPreference = "Continue"

# Color definitions
$ColorSuccess = "Green"
$ColorError = "Red"
$ColorWarning = "Yellow"
$ColorInfo = "Cyan"

function Write-ColorOutput($Message, $Color) {
    Write-Host $Message -ForegroundColor $Color
}

function Test-CommandExists($Command) {
    $exists = $null -ne (Get-Command $Command -ErrorAction SilentlyContinue)
    return $exists
}

function Test-Port($Port) {
    try {
        $tcp = New-Object System.Net.Sockets.TCPClient
        $tcp.Connect("localhost", $Port)
        $tcp.Close()
        return $true
    } catch {
        return $false
    }
}

function Wait-ForPort($Port, $ServiceName) {
    Write-ColorOutput "`n[$ServiceName] Waiting for port $Port to start..." $ColorInfo
    
    for ($i = 1; $i -le $MaxRetries; $i++) {
        if (Test-Port $Port) {
            Write-ColorOutput "[$ServiceName] Port $Port is ready!" $ColorSuccess
            return $true
        }
        Start-Sleep -Seconds $WaitInterval
        Write-Progress -Activity "Waiting for $ServiceName to start" -Status "Attempt $i/$MaxRetries"
    }
    
    Write-ColorOutput "[$ServiceName] Timeout waiting for port $Port!" $ColorError
    return $false
}

function Stop-Services($BackendJob, $FrontendJob) {
    if ($BackendJob) {
        $BackendJob | Stop-Job -ErrorAction SilentlyContinue
        $BackendJob | Remove-Job -ErrorAction SilentlyContinue
    }
    if ($FrontendJob) {
        $FrontendJob | Stop-Job -ErrorAction SilentlyContinue
        $FrontendJob | Remove-Job -ErrorAction SilentlyContinue
    }
}

# Main script starts here
Write-ColorOutput "======================================" $ColorInfo
Write-ColorOutput "    Smart MES System Launcher" $ColorInfo
Write-ColorOutput "======================================" $ColorInfo

# 1. Environment Check
Write-ColorOutput "`n[Environment Check] Checking runtime environment..." $ColorInfo

# Check Java
if (-not (Test-CommandExists "java")) {
    Write-ColorOutput "[ERROR] Java is not installed or not in PATH!" $ColorError
    exit 1
}
$javaVersion = (java -version 2>&1) | Select-Object -First 1
Write-ColorOutput "[OK] Java installed: $javaVersion" $ColorSuccess

# Check Node.js
if (-not (Test-CommandExists "node")) {
    Write-ColorOutput "[ERROR] Node.js is not installed or not in PATH!" $ColorError
    exit 1
}
$nodeVersion = node -v
Write-ColorOutput "[OK] Node.js installed: $nodeVersion" $ColorSuccess

# Check npm
if (-not (Test-CommandExists "npm")) {
    Write-ColorOutput "[ERROR] npm is not installed!" $ColorError
    exit 1
}
$npmVersion = npm -v
Write-ColorOutput "[OK] npm installed: $npmVersion" $ColorSuccess

# Check Maven (optional)
$mavenAvailable = Test-CommandExists "mvn"
if ($mavenAvailable) {
    $mvnVersion = (mvn -v 2>&1) | Select-Object -First 1
    Write-ColorOutput "[OK] Maven installed: $mvnVersion" $ColorSuccess
} else {
    Write-ColorOutput "[WARNING] Maven not found, will check for pre-built JAR file" $ColorWarning
}

# 2. Port Check
Write-ColorOutput "`n[Port Check] Checking service ports..." $ColorInfo

if (Test-Port $BackendPort) {
    Write-ColorOutput "[WARNING] Backend port $BackendPort is already in use!" $ColorWarning
}

if (Test-Port $FrontendPort) {
    Write-ColorOutput "[WARNING] Frontend port $FrontendPort is already in use!" $ColorWarning
}

# 3. Start Backend Service
Write-ColorOutput "`n[Backend Startup] Starting Spring Boot service..." $ColorInfo

$backendFullPath = Join-Path $PWD $BackendDir

if (-not (Test-Path $backendFullPath)) {
    Write-ColorOutput "[ERROR] Backend directory not found: $backendFullPath" $ColorError
    exit 1
}

$jarFile = Join-Path $backendFullPath "target/smartmes-proj-1.0.0.jar"
$backendJob = $null

if (Test-Path $jarFile) {
    # Use pre-built JAR file
    Write-ColorOutput "[Backend] Found pre-built JAR file" $ColorInfo
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:backendFullPath
        Write-Host "Entering directory: $using:backendFullPath"
        java -jar $using:jarFile 2>&1
    }
} elseif ($mavenAvailable) {
    # Use Maven to run
    Write-ColorOutput "[Backend] Using Maven spring-boot:run" $ColorInfo
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:backendFullPath
        Write-Host "Entering directory: $using:backendFullPath"
        mvn spring-boot:run 2>&1
    }
} else {
    Write-ColorOutput "[ERROR] No JAR file found and Maven is not available!" $ColorError
    Write-ColorOutput "[ERROR] Please install Maven or compile the project first." $ColorError
    exit 1
}

Write-ColorOutput "[Backend] Starting, please wait..." $ColorInfo

$backendStarted = $false
for ($i = 1; $i -le $MaxRetries; $i++) {
    Start-Sleep -Seconds $WaitInterval
    
    $jobResult = $backendJob | Receive-Job 2>&1
    
    # Check for Spring Boot startup success patterns
    if ($jobResult -match "Started Application" -or $jobResult -match "Tomcat started on port") {
        Write-ColorOutput "[Backend] Spring Boot service started successfully!" $ColorSuccess
        $backendStarted = $true
        break
    }
    
    if ($jobResult -match "ERROR|Failed") {
        Write-ColorOutput "[Backend] Startup failed: $jobResult" $ColorError
        Stop-Services $backendJob $null
        exit 1
    }
    
    Write-Progress -Activity "Waiting for backend service" -Status "Attempt $i/$MaxRetries"
}

if (-not $backendStarted) {
    Write-ColorOutput "[Backend] Backend service startup timeout!" $ColorError
    Stop-Services $backendJob $null
    exit 1
}

Wait-ForPort $BackendPort "Backend Service"

# 4. Start Frontend Service
Write-ColorOutput "`n[Frontend Startup] Starting Vue application..." $ColorInfo

$frontendFullPath = Join-Path $PWD $FrontendDir

if (-not (Test-Path $frontendFullPath)) {
    Write-ColorOutput "[ERROR] Frontend directory not found: $frontendFullPath" $ColorError
    Stop-Services $backendJob $null
    exit 1
}

$frontendJob = Start-Job -ScriptBlock {
    Set-Location $using:frontendFullPath
    Write-Host "Entering directory: $using:frontendFullPath"
    
    if (-not (Test-Path "node_modules")) {
        Write-Host "Installing dependencies..."
        npm install 2>&1
    }
    
    npm run dev 2>&1
}

Write-ColorOutput "[Frontend] Starting, please wait..." $ColorInfo

$frontendStarted = $false
for ($i = 1; $i -le $MaxRetries; $i++) {
    Start-Sleep -Seconds $WaitInterval
    
    $jobResult = $frontendJob | Receive-Job 2>&1
    
    if ($jobResult -match "Local.*$using:FrontendPort") {
        Write-ColorOutput "[Frontend] Vue application started successfully!" $ColorSuccess
        $frontendStarted = $true
        break
    }
    
    if ($jobResult -match "ERROR|ERR_|Failed") {
        Write-ColorOutput "[Frontend] Startup failed: $jobResult" $ColorError
        Stop-Services $backendJob $frontendJob
        exit 1
    }
    
    Write-Progress -Activity "Waiting for frontend application" -Status "Attempt $i/$MaxRetries"
}

if (-not $frontendStarted) {
    Write-ColorOutput "[Frontend] Frontend application startup timeout!" $ColorError
    Stop-Services $backendJob $frontendJob
    exit 1
}

Wait-ForPort $FrontendPort "Frontend Application"

# 5. Startup Complete
Write-ColorOutput "`n======================================" $ColorInfo
Write-ColorOutput "    Smart MES System Started!" $ColorSuccess
Write-ColorOutput "======================================" $ColorInfo
Write-ColorOutput "`nService URLs:" $ColorInfo
Write-ColorOutput "Backend API:    http://localhost:$BackendPort/smartmes/api" $ColorSuccess
Write-ColorOutput "Frontend App:   http://localhost:$FrontendPort" $ColorSuccess
Write-ColorOutput "`nTips:" $ColorInfo
Write-ColorOutput "- Press Ctrl+C to stop script (services continue running)" $ColorInfo
Write-ColorOutput "- To stop services, close the PowerShell windows" $ColorInfo
Write-ColorOutput "- Default login: username 'admin', password '123456'" $ColorInfo
Write-ColorOutput "`n======================================" $ColorInfo

Write-Host "`nPress any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

Stop-Services $backendJob $frontendJob

Write-ColorOutput "`nServices stopped. Thank you!" $ColorInfo