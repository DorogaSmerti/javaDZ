param([switch]$Test)

$ErrorActionPreference = 'Stop'
$jdkBin = Join-Path $env:LOCALAPPDATA 'Programs\Temurin-21\jdk-21.0.13+9\bin'
$className = 'Main'
if ($Test) { $className = 'CalculatorTest' }

Push-Location -LiteralPath $PSScriptRoot
$previousEncoding = [Console]::OutputEncoding
try {
    [Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
    & (Join-Path $jdkBin 'javac.exe') -encoding UTF-8 -d bin Calculator.java InvalidInputException.java Main.java CalculatorTest.java
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

    & (Join-Path $jdkBin 'java.exe') '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' -cp bin $className
    exit $LASTEXITCODE
} finally {
    [Console]::OutputEncoding = $previousEncoding
    Pop-Location
}
