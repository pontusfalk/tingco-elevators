# Elevator Coding Challenge  

The two interfaces `Elevator` and `ElevatorController` have been implemented for a Tingco-based elevator shaft.

#### Configure Tingco elevators  
`application.properties` has a few configuration settings: the number of floors and elevators as well as their speed.

#### Test Tingco elevators  
Safety first! Run `mvn test` to verify that the elevators are working as expected. 
Feel free to have a look at the tests as they serve as documentation for what features are implemented.

#### Run and play with Tingco elevators  
Start the app with `mvn spring-boot:run`  
There are a few endpoints to control the elevators:  

`/api/elevators/config GET` to get the number of elevators and floors used in this elevator shaft, as well as their speed.  
`/api/elevators GET` to get a list of all elevators and their info.  
`/api/elevators/floors/{floor number} POST` with an empty body to request an elevator to the floor number.  
`/api/elevators/{elevator id} PUT` with a body specifying the floor to move an elevator to a new floor.  
`/api/elevators/{elevator id} DELETE` to release the elevator with the given id.  

Some examples using `curl`:  
```
curl -v "http://localhost:8080/api/elevators/config"
curl -v "http://localhost:8080/api/elevators"
curl -v "http://localhost:8080/api/elevators/floors/4" -X POST --data ""
curl -v "http://localhost:8080/api/elevators/3" -H "Content-Type: application/json" -X PUT  --data '{"addressedFloor": 1}'
curl -v "http://localhost:8080/api/elevators/3" -X DELETE
```

Read the log messages written to stdout to verify the elevators are behaving like expected.

#### Todos and what's next  
Life as an elevator builder never ends. I have sprinkled my code with some todos with my thoughts on what would be
good to have a look at in the future.  
I had begun to work on a React frontend using Spring's websockets support for a more appealing user experience.
However, time got in the way and so I went with the safe option: REST and logging.  
Another thought was to put in a SQLite and use Spring Data to persist the elevator state between runs.   
