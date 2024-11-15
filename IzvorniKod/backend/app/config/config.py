import os
import secrets
from dataclasses import field
from typing import Literal

from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(
        # Use top level .env file (one level above ./backend/)
        env_file="../.env",
        env_ignore_empty=True,
        extra="ignore",
    )

    API_V1_STR: str = "/api/v1"
    SECRET_KEY: str = secrets.token_urlsafe(32)
    ENVIRONMENT: Literal["development", "test", "production"] = "development"
    # 60 minutes * 24 hours * 8 days = 8 days
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 60 * 24 * 8
    # JSON-formatted list of origins
    BACKEND_CORS_ORIGINS: list = field(default_factory=list)
    PROJECT_NAME: str = os.getenv("PROJECT_NAME")
    FIRST_SUPERUSER: str = os.getenv("FIRST_SUPERUSER")
    FIRST_SUPERUSER_PASSWORD: str = os.getenv("FIRST_SUPERUSER_PASSWORD")

    # database configurations
    MONGO_HOST: str = os.getenv("MONGO_HOST")
    MONGO_PORT: int = os.getenv("MONGO_PORT")
    MONGO_USER: str | None = os.getenv("MONGO_USER")
    MONGO_PASSWORD: str | None = os.getenv("MONGO_PASSWORD")
    MONGO_DB: str = os.getenv("MONGO_DB")


settings = Settings()  # type: ignore
