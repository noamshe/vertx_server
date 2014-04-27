var vertx = require('vertx');
var console = require('vertx/console');

var server = vertx.createNetServer();

server.connectHandler(function(sock) {

    sock.dataHandler(function(buffer) {
        console.log('I received ' + buffer.length() + ' bytes of data');
    });

}).listen(1234, 'localhost');

