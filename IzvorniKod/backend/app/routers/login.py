from datetime import timedelta
from typing import Any
from app import models, schemas
from app.auth.auth import (
    authenticate_user,
    create_access_token,
    get_current_user,
)
from app.config.config import settings
from fastapi import APIRouter, Body, Depends, HTTPException
from pydantic.networks import EmailStr

router = APIRouter()

@router.post("/access-token", response_model=schemas.Token)
async def login_access_token(password: str = Body(...), email: EmailStr = Body(...)) -> Any:
    """
    OAuth2 compatible token login, get an access token for future requests
    """
    user = await authenticate_user(email, password)
    if not user:
        raise HTTPException(status_code=400, detail="Incorrect email or password")
    elif not user.is_active:
        raise HTTPException(status_code=400, detail="Inactive user")
    access_token_expires = timedelta(minutes=settings.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = create_access_token(user.uuid, expires_delta=access_token_expires)
    return {
        "access_token": access_token,
        "token_type": "bearer",
    }


@router.get("/test-token", response_model=schemas.User)
async def test_token(current_user: models.User = Depends(get_current_user)) -> Any:
    """
    Test access token
    """
    return current_user


@router.get("/refresh-token", response_model=schemas.Token)
async def refresh_token(
    current_user: models.User = Depends(get_current_user),
) -> Any:
    """
    Return a new token for current user
    """
    if not current_user.is_active:
        raise HTTPException(status_code=400, detail="Inactive user")
    access_token_expires = timedelta(minutes=settings.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = create_access_token(
        current_user.uuid, expires_delta=access_token_expires
    )
    return {
        "access_token": access_token,
        "token_type": "bearer",
    }
