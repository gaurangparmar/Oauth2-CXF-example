# Oauth2-CXF-example
Apache CXF Oauth2 example with JWT/JWS/JWE

Example covers all 3 participant for complete oauth2 flow implemented using Apache CXF Oauth2.

Need to build individual project and it will deploy respective war file in Jboss/Wildfly server.

oauth2-server - register client app, generate grants, generate access token, validate token.  
api-server - api wich will call oauth2 server for validating tokens.  
client-app-server - third party who is willing to use services of api server.  

Go in each individual project folder and run

$ mvn clean install