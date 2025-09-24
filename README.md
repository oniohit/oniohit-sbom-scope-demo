# sbom-scope-demo

This minimal Maven project reproduces a common scenario where **test-scope** dependencies
(e.g., JUnit 4 via vintage engine, Mockito, ByteBuddy, Objenesis) show up in a CycloneDX SBOM
generated from source, even though they are not part of the runtime artifact.

## 1) Inspect dependency scopes
```bash
mvn -q dependency:tree
# look for ':test' at the end of lines (e.g., junit:junit:4.13.x:test)
```

## 2) Generate a CycloneDX SBOM from source (includes test-scope)
```bash
# Using the CycloneDX Maven plugin (no extra config required)
mvn -q org.cyclonedx:cyclonedx-maven-plugin:makeBom
# Output lands under target/ (e.g., bom.xml or bom.json)
```

## 3) Build the runtime artifact (excludes test deps)
```bash
mvn -q -DskipTests package
# Produces target/sbom-scope-demo-1.0.0.jar
```

## 4) Generate a CycloneDX SBOM from the built JAR (runtime-only view)
```bash
# Using Trivy to generate a CycloneDX SBOM from the compiled JAR:
trivy sbom --format cyclonedx -o sbom-runtime.cdx.json target/sbom-scope-demo-1.0.0.jar
# This JAR-based SBOM will **not** include test-only dependencies.
```

## 5) Compare
- SBOM from **source** likely includes JUnit 4, Mockito, ByteBuddy, Objenesis, etc.
- SBOM from **JAR** should only include runtime dependencies (e.g., jackson-databind, commons-lang3).

> If you only want production dependencies in your SBOM, prefer generating from the final artifact (JAR/WAR)
> or post-process the source SBOM to filter out entries not present in non-test scopes of `mvn dependency:tree`.
