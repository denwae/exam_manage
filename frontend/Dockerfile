FROM node:20-alpine as build
COPY exam-manage/package.json exam-manage/package-lock.json ./
RUN npm ci
COPY exam-manage .
RUN npm run build

FROM nginx:latest
COPY nginx.conf /etc/nginx/templates/nginx.conf.template
COPY --from=build /build /usr/share/nginx/html
EXPOSE 80