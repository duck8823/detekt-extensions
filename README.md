# detekt-extensions

This is extensions for [detekt](https://github.com/arturbosch/detekt).  

## Usage
### build.gradle
```groovy
dependencies {
    detekt "com.github.duck8823:detekt-extensions:0.0.1-SNAPSHOT"
}
```

### pom.xml
```xml
<plugin>
    <groupId>com.github.ozsie</groupId>
    <artifactId>detekt-maven-plugin</artifactId>
    <version>${detekt.version}</version>
    <configuration>
        <config>path/to/detekt-config.yml</config>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>com.github.duck8823</groupId>
            <artifactId>detekt-extensions</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</plugin>
```

## Extensions
### ComplexityBuildFailureReport
This extension support more extended conditions to build failure.  
You can add failure threshold that reported by ComplexityReport (that is built in detekt).  
By default, Each thresholds is `Int.MAX_VALUE`.  
You can set thresholds in `detekt-config.yml`.  

```yaml
complexityThresholds:
  loc: 100
  sloc: 100
  lloc: 100
  cloc: 100
  mcc: 100
  smells: 100
  mccPerThousandLLOC: 100
  smellsPerThousandLLOC: 100
```
