import requests
import json
import time
import random

# Define the base URL of your REST API
BASE_URL = "http://localhost:8080/api"

# Sample data to be sent to the endpoints
take_event = {
    "act": "take",
    "customerId": "1",
    "productGroup": "Bread",
    "price": 100
}

return_event = {
    "act": "return",
    "customerId": "1",
    "productGroup": "Bread",
    "price": 100
}

JWT_TOKEN='eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YW1hcmFpbGljMTFAZ21haWwuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsIm5hbWUiOiJUYXNhIElsaWMiLCJpZCI6NiwiZXhwIjoxNzIwNzQwMTgyLCJpYXQiOjE3MTgwNjE3ODJ9.eOjeeLsgHPlOoXw1P-WHVBlHv675KyNQGyB3MFP0EI2hzrQNKzqg8HyrAFNW3V-iHX6Tw1Ly-nnaJvc7lQDEuQ'

headers = {
    'Authorization': f'Bearer {JWT_TOKEN}',
    'Content-Type': 'application/json'
}

# Function to send a POST request
def send_post_request(data):
    url = f"{BASE_URL}/product_detection?productGroup=Bread&act={data}&customerId=1&price=333"
    response = requests.post(url, headers=headers)
    return response

for i in range(288):
    wait = random.choice([13,16])
    # Trigger take event
    response_take = send_post_request("take")
    print(f"Take Event Response: {response_take.status_code} - {response_take.text}")

    # Wait for some time before triggering the return event to simulate real-time events
    time.sleep(wait)

    # Trigger return event
    response_return = send_post_request("return")
    print(f"Return Event Response: {response_return.status_code} - {response_return.text}")

    # Further steps can be added to trigger other events and test different Drools rules.
