import requests
import json
import random
import time

# URL of the Drools service REST API
CONT_READING_ENDPOINT='http://localhost:8080/api/sensor/continuous_reading?'
DISC_READING_ENDPOINT='http://localhost:8080/api/sensor/discret_reading?'
CAMERA_READING_ENDPOINT='http://localhost:8080/api/sensor/camera_reading?'
SECURITY_READING_ENDPOINT='http://localhost:8080/api/sensor/security_reading?'


JWT_TOKEN='eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YW1hcmFpbGljMTFAZ21haWwuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsIm5hbWUiOiJUYW1hcmEgSWxpYyIsImlkIjoxLCJleHAiOjE3MjA2MTc4ODMsImlhdCI6MTcxNzkzOTQ4M30.jjpew0lNN9nj0s1Yp52UhpWosaUvG3FnPHSWRHjfOWlBeoBroaqpzTMsMa51cQnb8h03JaFq118r4kvsnMNvYw'

headers = {
    'Authorization': f'Bearer {JWT_TOKEN}',
    'Content-Type': 'application/json'
}

contioues_sensor_ids = [4,5]
value = random.randint(-3,10)
def send_cont_reading(sensor_id, value):
    response = requests.put(f'{CONT_READING_ENDPOINT}sensorId={sensor_id}&value={value}', headers=headers)
    if response.status_code == 200:
        print("Event sent successfully:")
    else:
        print("Failed to send event:", response.status_code, response.text)

discrete_sensor_id = [3,6]
def send_disc_reading(sensor_id):
    response = requests.put(f'{DISC_READING_ENDPOINT}sensorId={sensor_id}', headers=headers)
    if response.status_code == 200:
        print("Event sent successfully:")
    else:
        print("Failed to send event:", response.status_code, response.text)

camera_sensor_id = 2
camera_types = ['cameraDetect', 'detectTempWarm', 'detectTempCold','faceRecognitionFalse','faceRecognitionTrue']
def send_camera_reading(type, sensor_id):
    response = requests.put(f'{CAMERA_READING_ENDPOINT}type={type}&sensorId={sensor_id}', headers=headers)
    if response.status_code == 200:
        print("Event sent successfully:")
    else:
        print("Failed to send event:", response.status_code, response.text)


security_sensor_ids = [1,2]
leaf_room_ids = [3,4,5]
security_types = ['securityDetect','securityFaceRecognitionFalse','securityFaceRecognitionTrue']
def send_security_reading(type,sensor_id):
    response = requests.put(f'{SECURITY_READING_ENDPOINT}type={type}&sensorId={sensor_id}', headers=headers)
    if response.status_code == 200:
        print("Event sent successfully:")
    else:
        print("Failed to send event:", response.status_code, response.text)
    
if __name__ == "__main__":
    # Scenario1 Execution
    send_disc_reading(sensor_id=3)
    time.sleep(1)
    send_camera_reading(type='cameraDetect', sensor_id=2)
    time.sleep(1)
    send_camera_reading(type='detectTempWarm', sensor_id=2)
    time.sleep(1)
    send_camera_reading(type='faceRecognitionFalse', sensor_id=2)
    time.sleep(1)
    send_cont_reading(sensor_id=6, value=8)

    # Scenario2 Execution
    # send_disc_reading(sensor_id=3)
    # send_camera_reading(type='cameraDetect', sensor_id=2)
    # send_camera_reading(type='detectTempWarm', sensor_id=2)
    # send_camera_reading(type='faceRecognitionTrue', sensor_id=2)

    # # Scenario3 Execution
    # send_disc_reading(sensor_id=3)
    # send_camera_reading(type='cameraDetect', sensor_id=2)
    # send_camera_reading(type='detectTempCold', sensor_id=2)

    # # Scenario4 Execution
    # send_cont_reading(sensor_id=4, value=8)
    # time.sleep(5)
    # send_security_reading('securityDetect',1)
    # time.sleep(5)
    # send_security_reading('securityFaceRecognitionFalse',1)
    # send_cont_reading(sensor_id=4, value=8)
    # # Scenario4 Execution
    # send_disc_reading(sensor_id=3)
    # send_security_reading(type='securityDetect', sensor_id=2, room_id=4)
    # send_security_reading(type='securityFaceRecognitionTrue', sensor_id=2, room_id=4)

