# Étape 1 : build Angular
FROM node:22-alpine AS build
WORKDIR /app
COPY campushub-front/package*.json ./
RUN npm ci
COPY campushub-front/ .
RUN npm run build

# Étape 2 : image Nginx légère
FROM nginx:alpine
COPY --from=build /app/dist/campushub-front/browser /usr/share/nginx/html
COPY docker/front/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80