import requests
import json
import time
import random

# Define the base URL of your REST API
BASE_URL = "http://localhost:8080/api/people_detection"

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
def total_daily():
    url = f"{BASE_URL}/total_daily?location=Level 1 Room 1&startDate=Thu February 30 12:30:00 GMT 2024&endDate=Thu Jun 01 12:30:00 GMT 2024"
    response = requests.get(url, headers=headers)
    return response

for i in range(288):
   total_daily()
