from pydantic import BaseModel
from typing import Optional


class Item(BaseModel):
    id: Optional[str]
    name: str
    description: Optional[str] = None
