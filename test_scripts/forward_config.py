import requests
import json
import time
import random
POST_CONFIG = "http://localhost:8080/api/room/config"
POST_RESPONSES = "http://localhost:8080/api/room/config/requests"

securityLevel = ['HIGH','MEDIUM','LOW']
type = ['work','living']
size = random.randint(1,15)

config = {'config':[{
    'roomId':3,
    'size':11,
    'securityLevel':'HIGH',
    'type':'work'
},{
    'roomId':4,
    'size':11,
    'securityLevel':'MEDIUM',
    'type':'work'
},
{
    'roomId':5,
    'size':9,
    'securityLevel':'LOW',
    'type':'work'
},

]}

response = [
    {
        'workRequest':[],
        'extraGearRequest':[],
        'sensors':[]
    },
    {
        'workRequest':[],
        'extraGearRequest':[],
        'sensors':[]
    },
    {
        'workRequest':[],
        'extraGearRequest':[],
        'sensors':[]
    }
]

request_responses1 = {
    'config':[
        {
   'workResponse':
   {
       'roomId':'3',
       'type':'bring_electricity',
       'success':'false'
       },
    'extraGearResponse':None,
    'roomId':'3'
},{
   'workResponse':{'roomId':'4','type':'bring_electricity','success':'false'},
    'extraGearResponse':None,
    'roomId':'4'
},
{
   'workResponse':{'roomId':'5','type':'bring_electricity','success':'false'},
    'extraGearResponse':None,
    'roomId':'5'
},

]}

request_responses2 = {
    'config':[
        {
   'workResponse':None,
    'extraGearResponse':{'roomId':'3','type':'extra_battery','response':'false'},
    'roomId':'3'
},{
   'workResponse':None,
    'extraGearResponse':{'roomId':'4','type':'extra_battery','response':'true'},
    'roomId':'4'
},
{
   'workResponse':None,
    'extraGearResponse':None,
    'roomId':'4'
},

]}

request_responses3 = {
    'config':[
        {
   'workResponse':{'roomId':'3','type':'dedicated_server_for_memory','success':'true'},
    'extraGearResponse':None,
    'roomId':'3'
},{
   'workResponse':None,
    'extraGearResponse':None,
    'roomId':'4'
},
{
   'workResponse':None,
    'extraGearResponse':None,
    'roomId':'5'
},

]}

JWT_TOKEN='eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YW1hcmFpbGljMTFAZ21haWwuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsIm5hbWUiOiJUYW1hcmEgSWxpYyIsImlkIjoxLCJleHAiOjE3MjA2MTc4ODMsImlhdCI6MTcxNzkzOTQ4M30.jjpew0lNN9nj0s1Yp52UhpWosaUvG3FnPHSWRHjfOWlBeoBroaqpzTMsMa51cQnb8h03JaFq118r4kvsnMNvYw'

headers = {
    'Authorization': f'Bearer {JWT_TOKEN}',
    'Content-Type': 'application/json'
}

def post_config():
    response = requests.post(f'{POST_CONFIG}', headers=headers, json=config)
    if response.status_code == 200:
        print(json.dumps(response.json(), indent=4))
        return
    else:
        print("Failed to get config:", response.status_code, response.text)

def post_responses(request_responses):
    response = requests.post(f'{POST_RESPONSES}', headers=headers, json=request_responses)
    if response.status_code == 200:
        print(json.dumps(response.json(), indent=4))
    else:
        print("Failed to get config:", response.status_code, response.text)


if __name__ == '__main__':
    post_config()
    time.sleep(2)
    post_responses(request_responses1)
    time.sleep(2)
    post_responses(request_responses2)
    time.sleep(2)
    post_responses(request_responses3)
    