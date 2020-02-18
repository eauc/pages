FROM node:12 as build

RUN apt update && \
    apt install -y openjdk-8-jre && \
    apt-get clean

WORKDIR /app
RUN mkdir -p ./public/app/js
RUN chown -R node:node /app
USER node

COPY ./package* ./

RUN npm install

COPY ./shadow-cljs.edn ./
RUN npx shadow-cljs npm-deps

COPY ./src ./src
COPY ./public ./public
RUN npx shadow-cljs compile app

FROM build

CMD ["npx","shadow-cljs","watch","app"]
