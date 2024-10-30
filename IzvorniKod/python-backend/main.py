from fastapi import FastAPI, HTTPException
from database import collection
from models import Item
from bson import ObjectId

app = FastAPI()


@app.post("/items/")
async def create_item(item: Item):
    result = await collection.insert_one(item.dict())
    return {"id": str(result.inserted_id)}


@app.get("/items/{item_id}")
async def read_item(item_id: str):
    item = await collection.find_one({"_id": ObjectId(item_id)})
    if not item:
        raise HTTPException(status_code=404, detail="Item not found")
    item["id"] = str(item["_id"])
    return item


@app.put("/items/{item_id}")
async def update_item(item_id: str, item: Item):
    result = await collection.update_one({"_id": ObjectId(item_id)}, {"$set": item.dict()})
    if result.modified_count == 0:
        raise HTTPException(status_code=404, detail="Item not found")
    return {"status": "Item updated"}


@app.delete("/items/{item_id}")
async def delete_item(item_id: str):
    result = await collection.delete_one({"_id": ObjectId(item_id)})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Item not found")
    return {"status": "Item deleted"}


@app.get("/")
async def root():
    return {"message": "Hello World"}

