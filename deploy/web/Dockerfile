# syntax=docker/dockerfile:1
FROM node:14.21.3-bullseye AS client-build
WORKDIR /build
COPY deploy/web/prepare-lock.js /tmp/prepare-lock.js
COPY front/cate-client/package.json front/cate-client/package-lock.json ./
RUN node /tmp/prepare-lock.js \
    && npm ci --ignore-scripts --no-audit --no-fund --registry=https://registry.npmjs.org \
    && node node_modules/node-sass/scripts/install.js
COPY front/cate-client/ ./
ENV CATE_API_BASE=/api
RUN npm run build

FROM node:14.21.3-bullseye AS manage-build
WORKDIR /build
COPY deploy/web/prepare-lock.js /tmp/prepare-lock.js
COPY front/cate-manage/package.json front/cate-manage/package-lock.json ./
RUN node /tmp/prepare-lock.js \
    && npm ci --ignore-scripts --no-audit --no-fund --registry=https://registry.npmjs.org
COPY front/cate-manage/ ./
ENV CATE_API_BASE=/api
RUN npm run build

FROM nginx:1.28-alpine
COPY deploy/web/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=client-build /build/dist/ /usr/share/nginx/html/client/
COPY --from=manage-build /build/dist/ /usr/share/nginx/html/manage/
EXPOSE 8080 8081
