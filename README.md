![](https://img.shields.io/github/license/majidhameed/moneychanger?style=plastic)

![Readme Card](https://github-readme-stats.vercel.app/api/pin/?username=majidhameed&repo=moneychanger&show_owner=true)
# MONEY CHANGER

---
### Tested Environment / Requirements
#### JAVA SDK 11
openjdk version "11.0.2" 2019-01-15
OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.2+9, mixed mode)

#### MAVEN 3
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: C:\IT_CodeRepo\Installed-Soft\maven3\bin\..
Java version: 11.0.2, vendor: Oracle Corporation, runtime: C:\IT_CodeRepo\Installed-Soft\Java\jdk-11.0.2
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

---
## How to run?
### Change to where zip file is extracted
```
cd moneychanger
```

## RUN
```
mvn clean spring-boot:run
```

## TEST
```
mvn test
```
---

>## REST REQUESTS

>### EXCHANGE RATE
#### CREATE
```
curl --location --request POST 'http://localhost:8080/api/v1/rest/exchangerate' 
--header 'Content-Type: application/json' 
--data-raw '{"currencyCode":"pkr", "buyRate": 130.51, "sellRate":130.25, "currencyTitle":"Pakistani Rupees"}' | jq
```

#### READ ALL 
```
curl --location --request GET 'http://localhost:8080/api/v1/rest/exchangerate' | jq
```

#### READ
```
curl --location --request GET 'http://localhost:8080/api/v1/rest/exchangerate/1' | jq
```

#### UPDATE
```
curl --location --request PUT 'http://localhost:8080/api/v1/rest/exchangerate/3' 
--header 'Content-Type: application/json' 
--data-raw ' {"currencyCode": "PKR", "currencyTitle": "Pakistani Rupee Currency", "buyRate": 130.5100, "sellRate": 130.2500}' | jq 
```

#### DELETE
```
curl --location --request DELETE 'http://localhost:8080/api/v1/rest/exchangerate/3' | jq
```

#### READ EXCHANGE RATE BY CURRENCY CODE
```
curl --location --request GET 'http://localhost:8080/api/v1/rest/exchangerate/cc/USD' | jq
```

> ### EXCHANGE MONEY
#### 200 SGD TO USD
```
curl --location --request POST 'http://localhost:8080/api/v1/rest/exchangemoney' --header 'Content-Type: application/json' 
--data-raw '{"givenAmount":200, "givenCurrencyCode":"SGD", "convertCurrencyCode":"USD"}' | jq
```

#### 200 SGD TO HKD
```
curl --location --request POST 'http://localhost:8080/api/v1/rest/exchangemoney' --header 'Content-Type: application/json' 
--data-raw '{"givenAmount":200, "givenCurrencyCode":"SGD", "convertCurrencyCode":"HKD"}' | jq
```
