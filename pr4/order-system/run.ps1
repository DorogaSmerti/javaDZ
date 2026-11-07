$ErrorActionPreference = 'Stop'
$jdkBin = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin' } else { '' }
$installedJdk = Join-Path $env:LOCALAPPDATA 'Programs\Temurin-21\jdk-21.0.13+9\bin'
if (!$jdkBin -and (Test-Path -LiteralPath $installedJdk)) { $jdkBin = $installedJdk }
$javac = if ($jdkBin) { Join-Path $jdkBin 'javac.exe' } else { 'javac.exe' }
$java = if ($jdkBin) { Join-Path $jdkBin 'java.exe' } else { 'java.exe' }

Push-Location -LiteralPath $PSScriptRoot
$previousEncoding = [Console]::OutputEncoding
try {
    [Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
    $lib = '.build/lib'
    New-Item -ItemType Directory -Force $lib, '.build/modules' | Out-Null
    $dependencies = @{'lombok.jar'='org/projectlombok/lombok/1.18.38/lombok-1.18.38.jar'
    'javafaker.jar'='com/github/javafaker/javafaker/1.0.2/javafaker-1.0.2.jar'
    'snakeyaml.jar'='org/yaml/snakeyaml/1.23/snakeyaml-1.23-android.jar'
    'commons-lang3.jar'='org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar'
    'generex.jar'='com/github/mifmif/generex/1.0.2/generex-1.0.2.jar'
    'automaton.jar'='dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar'}
    foreach ($dependency in $dependencies.GetEnumerator()) {
        $jar = Join-Path $lib $dependency.Key
        if (!(Test-Path -LiteralPath $jar)) {
            curl.exe -fsSL "https://repo.maven.apache.org/maven2/$($dependency.Value)" -o "$jar.download"
            if ($LASTEXITCODE -ne 0) { throw "Could not download $($dependency.Key)." }
            Move-Item -LiteralPath "$jar.download" -Destination $jar
        }
    }

    & $javac --release 21 -encoding UTF-8 --module-path $lib --processor-path "$lib/lombok.jar" --module-source-path src -d .build/modules -m order.storage,order.processing,order.workers
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

    & $java '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' --module-path ".build/modules;$lib" -m order.workers/shop.workers.Main
    exit $LASTEXITCODE
} finally {
    [Console]::OutputEncoding = $previousEncoding
    Pop-Location
}
