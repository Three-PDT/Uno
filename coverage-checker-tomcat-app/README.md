# COVERAGE CHECKER
Coverage checker using embedded tomcat app
Reads maintenance data from DB
Reads copy from messages properties

## Running
mvn spring-boot:run

## TODO
locationValue
error

threeg
fourg
congestion
devicespeed
"devicespeed": {
"bodytext": "volte",	LTEResponseConstructor.devicespeed
"postcode": "800",
"speed": "1"
},
maintenance is taken from fatwire
threeg is taken from fatwire
fourg is taken from fatwire
devicespeed is all taken from coverage entity.

based on the int returned from COVERAGE_CHECKER table the following copy is returned and set into the threeg object
0 --> None
1,2 --> Excellent
3,4 --> Good
200 --> ComingSoon_SixMonths
100 --> ComingSoon_ThreeMonths


CoverageCheckerLTE API - Coverage 3G Good
CoverageCheckerLTE API - Coverage 3G None
CoverageCheckerLTE API - Coverage 3G Region Good
CoverageCheckerLTE API - Coverage 3G Region none
CoverageCheckerLTE API - Coverage 3G Region Very good
CoverageCheckerLTE API - Coverage 3G Very good



