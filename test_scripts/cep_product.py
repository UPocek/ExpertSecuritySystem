import requests
import json
import time

# Define the base URL of your REST API
BASE_URL = "http://localhost:8080/api"

# Sample data to be sent to the endpoints
take_event = {
    "act": "take",
    "customerId": "customer1",
    "productId": 1,
    "price": 100
}

return_event = {
    "act": "return",
    "customerId": "customer1",
    "productId": 1,
    "price": 100
}

# Function to send a POST request
def send_post_request(data):
    url = f"{BASE_URL}/product_detection"
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, params=data, headers=headers)
    return response

for i in range(10):
    # Trigger take event
    response_take = send_post_request(take_event)
    print(f"Take Event Response: {response_take.status_code} - {response_take.text}")

    # Wait for some time before triggering the return event to simulate real-time events
    time.sleep(13)

    # Trigger return event
    response_return = send_post_request(return_event)
    print(f"Return Event Response: {response_return.status_code} - {response_return.text}")

    # Further steps can be added to trigger other events and test different Drools rules.
