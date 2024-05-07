GET Mappings:

/reservations -> Get Request to find all the existing reservations
/reservations/{date} -> Get Reqeust to find all of the reservations for a specific Date

POST Mappings:
/reservation -> Request to add a new reservation
Base template for post request to create a reservation:
{
    "customerName":"",
    "tableSize": 3,
    "date":"MM-DD-YYYY",
    "time":"00:00"
}

App runs on port: 8080
