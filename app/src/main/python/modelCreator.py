import json
import requests
import numpy as np

def send_data(stockData):
    # Reshape the flattened list into a 2D NumPy array
    np_array = np.array(stockData).reshape((len(stockData) // 5, 5))

    # server_url = 'http://localhost:3000/upload'
    serverUrl = "http://192.168.1.26:5000/upload"


    # Data to be sent in the request (2D array)
    data_to_send = {'numbers': np_array.tolist()}

    # Send a POST request to the server
    response = requests.post(serverUrl, json=data_to_send)
    result = response.json()

    result = response.json()
    # double_value_str = result['data']
    # Convert the string to a float

    double_value = float(result)
    return double_value
  

   





