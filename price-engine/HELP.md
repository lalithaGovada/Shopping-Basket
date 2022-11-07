# Read Me First
      * The original package name 'com.ecom.priceengine'
      * PriceEngineController  is the REST service 
      * ProductEngineService   is the service /Business layer  --> if u pass "product name" ---> it will go DB layer get the price and display hear
      *---------------------  steps to test
      * 1. start REST webService by right click on top of project --> Run STS tahn open browser        http://localhost:2022/price?title=Jam
      * 2. check H2 db by http://localhost:2022/h2-console
      3*. Junit by right run as > application console
# Getting Started
### Reference Documentation
# Technologies used 
      * Spring Boot
      * H2 
      * JUnit
      * API
      * Integration testing
# Service - "PriceEngineApplication"
		Ex: Service : http://localhost:2022/price?title=Jam  :  Tested
		    H2 DB   : http://localhost:2022/h2-console       :  Tested
		    DB up and tested
           Amend  data.sql
       
# Integration Tests  - "PriceEngineApplicationTests"
          Tested the service with valid outcome (Sub total, Tax and Total) 

          
          
       