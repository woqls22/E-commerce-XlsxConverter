FROM node:16.14.0 as builder

RUN mkdir /usr/src/app
WORKDIR /usr/src/app
ENV PATH /usr/src/app/node_modules/.bin:$PATH
COPY package.json /usr/src/app/package.json

RUN yarn install --silent


# source -> working directory copy
# run
COPY .. /usr/src/app/
CMD ["yarn","start"]