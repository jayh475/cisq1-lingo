# CISQ1: Lingo Trainer

## installing database in docker 
```
docker-compose up  
```

## Project jacoco testing 
```
mvnw verify 
( results can be found in target/site/index.html )
```

## mutation test coverage with PITest
```
mvnw org.pitest:pitest-maven:mutationCoverage
( target/pit-reports/<DATUM>/index.html) 
```

## PITest Test keeping track of history between every run 
```
mvnw -DwithHistory org.pitest:pitest-maven:mutationCoverage
( results can be found in target/site/index.html )
```

## Selenium end to end testing
```
For testing the whole application we can use Selenium and selenium IDE 

```

## Testing dependencies with Dependency-check-maven
```
mvnw verify
( results can be found in target/dependency-check-report.html )

```





