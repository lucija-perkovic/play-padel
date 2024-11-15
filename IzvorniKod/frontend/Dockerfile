# syntax=docker.io/docker/dockerfile:1

FROM node:18-alpine

WORKDIR /app

COPY . /app
# Install dependencies based on the preferred package manager
RUN npm install

# Next.js collects completely anonymous telemetry data about general usage. Learn more here: https://nextjs.org/telemetry
# Uncomment the following line to disable telemetry at run time
# ENV NEXT_TELEMETRY_DISABLED 1

# Note: Don't expose ports here, Compose will handle that for us

# Start Next.js in development mode based on the preferred package manager
CMD npm run dev