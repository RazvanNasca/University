var koa = require('koa');
var app = module.exports = new koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');

app.use(bodyParser());

app.use(cors());

app.use(middleware);

function middleware(ctx, next) {
  const start = new Date();
  return next().then(() => {
    const ms = new Date() - start;
    console.log(`${start.toLocaleTimeString()} ${ctx.request.method} ${ctx.request.url} ${ctx.response.status} - ${ms}ms`);
  });
}


const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
};

const names = ['IMGROOT',
  'HAVA PUG',
  'Y U CRYN',
  'BOY',
  'EXPIRED',
  'GO FOXES',
  'LLWLWWW'];
const statuses = ['new', 'working', 'damaged', 'old'];
const drivers = ['Michael', 'Matthew', 'Mason', 'Maverick', 'Miles', 'Maxwell', 'Max'];
const colors = ['Yellow', 'Blue', 'Black', 'Green', 'White', 'Red'];
const vehicles = [];

for (let i = 0; i < 50; i++) {
  vehicles.push({
    id: i + 1,
    license: names[getRandomInt(0, names.length)] + " " + i,
    status: statuses[getRandomInt(0, statuses.length)],
    seats: getRandomInt(2, 20),
    driver: drivers[getRandomInt(0, drivers.length)],
    color: colors[getRandomInt(0, colors.length)],
    cargo: getRandomInt(0, 400)
  });
}

const router = new Router();

router.get('/all', ctx => {
  ctx.response.body = vehicles;
  ctx.response.status = 200;
});

router.get('/review', ctx => {
  ctx.response.body = vehicles;
  ctx.response.status = 200;
});


router.get('/colors', ctx => {
  ctx.response.body = [...new Set(vehicles.map(obj => obj.color))];
  ctx.response.status = 200;
});

router.get('/vehicles/:color', ctx => {
  const headers = ctx.params;
  const color = headers.color;
  if (typeof color !== 'undefined') {
    ctx.response.body = vehicles.filter(obj => obj.color == color);
    ctx.response.status = 200;
  } else {
    console.log("Missing or invalid: color!");
    ctx.response.body = {text: 'Missing or invalid: color!'};
    ctx.response.status = 404;
  }
});

router.get('/driver/:name', ctx => {
  const headers = ctx.params;
  const name = headers.name;
  if (typeof name !== 'undefined') {
    ctx.response.body = vehicles.filter(obj => obj.driver == name);
    ctx.response.status = 200;
  } else {
    console.log("Missing or invalid: driver name!");
    ctx.response.body = {text: 'Missing or invalid: driver name!'};
    ctx.response.status = 404;
  }
});


const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.post('/vehicle', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const license = headers.license;
  const seats = headers.seats;
  const driver = headers.driver;
  const color = headers.color;
  const cargo = headers.cargo;
  if (typeof license !== 'undefined' && typeof driver !== 'undefined' && typeof seats !== 'undefined'
    && color !== 'undefined' && cargo !== 'undefined') {
    const index = vehicles.findIndex(obj => obj.license == license);
    if (index !== -1) {
      console.log("Vehicle already exists!");
      ctx.response.body = {text: 'Vehicle already exists!'};
      ctx.response.status = 404;
    } else {
      let maxId = Math.max.apply(Math, vehicles.map(function (obj) {
        return obj.id;
      })) + 1;
      let obj = {
        id: maxId,
        license,
        status: 'new',
        seats,
        driver,
        color,
        cargo
      };
      // console.log("created: " + JSON.stringify(license));
      vehicles.push(obj);
      broadcast(obj);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid fields!");
    ctx.response.body = {text: 'Missing or invalid fields!'};
    ctx.response.status = 404;
  }
});

router.del('/vehicle/:id', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  if (typeof id !== 'undefined') {
    const index = vehicles.findIndex(obj => obj.id == id);
    if (index === -1) {
      console.log("No vehicle with id: " + id);
      ctx.response.body = {text: 'Invalid vehicle id'};
      ctx.response.status = 404;
    } else {
      let obj = vehicles[index];
      // console.log("deleting: " + JSON.stringify(obj));
      vehicles.splice(index, 1);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    console.log("Missing or invalid fields!");
    ctx.response.body = {text: 'Id missing or invalid'};
    ctx.response.status = 404;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(2021);
