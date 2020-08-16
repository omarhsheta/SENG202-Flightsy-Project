def get_lines(name):
    file = open(name, "r", encoding="utf8")
    contents = file.read()
    lines = contents.split('\n')
    file.close()
    return lines

def write_file(name, lines):
    file = open(name, "x", encoding="utf8")
    for line in lines:
        file.write(line + '\n')
    file.close()

def airline_encode(lines):
    statements = []
    for line in lines:
        try:
            id_airline, name, alias, iata, icao, callsign, country, active = line.split(",")
            insert = "insert into airline (id_airline, name, alias, iata, icao, callsign, country, active) "
            values = "values ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7})".format(id_airline, name, alias, iata, icao, callsign, country, active)
            statements.append(insert + values)
        except:
            pass
    write_file("airline_script.sql", statements)

def airport_encode(lines):
    statements = []
    for line in lines:
        try:
            id_airport, name, city, county, iata, icao, latitude, longitude, altitude, timezone, dst, _ = line.split(",")
            insert = "insert into airport (id_airport, name, city, county, iata, icao, latitude, longitude, altitude, timezone, dst) "
            values = "values ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10})".format(id_airport, name, city, county, iata, icao, latitude, longitude, altitude, timezone, dst)
            statements.append(insert + values)
        except:
            pass
    write_file("airport_script.sql", statements)

def route_encode(lines):
    statements = []
    for line in lines:
        try:
            airline, id_airline, source_airport, source_airport_id, destination_airport, destination_airport_id, codeshare, stops, equipment = line.split(",")
            insert = "insert into route (airline, id_airline, source_airport, source_airport_id, destination_airport, destination_airport_id, codeshare, stops, equipment) "
            values = "values ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8})".format(airline, id_airline, source_airport, source_airport_id, destination_airport, destination_airport_id, codeshare, stops, equipment)
            statements.append(insert + values)
        except:
            pass
    write_file("route_script.sql", statements)

def plane_encode(lines):
    pass

#lines = get_lines("airline.txt")
#airline_encode(lines)

lines = get_lines("airport.txt")
airport_encode(lines)

#lines = get_lines("route.txt")
#route_encode(lines)
    