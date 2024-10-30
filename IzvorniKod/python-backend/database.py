from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from pymongo.collection import Collection

MONGO_URI = "mongodb+srv://mihaelkukelscak:KX2d3ijaSMkOSjHe@play-padel-cluster.bq1gt.mongodb.net/?retryWrites=true&w=majority&appName=play-padel-cluster"
MONGO_DB = "play-padel-database-1"
MONGO_COLLECTION = "play-padel-collection-1"

client = MongoClient(MONGO_URI, server_api=ServerApi('1'))
database = client[MONGO_DB]
collection: Collection = database[MONGO_COLLECTION]

try:
    client.admin.command('ping')
    print("Pinged your deployment. You successfully connected to MongoDB!")
except Exception as e:
    print(e)
