let stompClient = null;
let messageTemplate = null;

document.addEventListener('DOMContentLoaded', () => {
  messageTemplate = Handlebars.compile(document.getElementById("message-template").innerHTML);

  const messagesEl = document.querySelector('#messages');
  messagesEl.scrollTop = messagesEl.scrollHeight - messagesEl.offsetHeight;

  connect();

  loadGroups();
});

function connect() {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', message => {
      addMessage(JSON.parse(message.body));
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log('Disconnected');
}

function onSubmitMessageForm() {
  sendMessage({content: document.querySelector('#send-message').value}, document.querySelector('#send-destination').value);
  return false;
}

function sendMessage(message, destination) {
  stompClient.send("/app/send", {'X-Destination': destination}, JSON.stringify(message));
}

function addMessage(message) {
  const messagesEl = document.querySelector('#messages');

  const scrolledToBottom = messagesEl.scrollTop >=
    (messagesEl.scrollHeight - messagesEl.offsetHeight - 8);

  const element = htmlToElement(messageTemplate(message));
  messagesEl.appendChild(element);

  if (scrolledToBottom) {
    element.scrollIntoView({
      behavior: 'instant',
      block: 'end',
      inline: 'nearest',
    });
  }
}

function loadGroups() {
  const groupsEl = document.querySelector('#send-destination');

  fetch('/api/destinations')
    .then(resp => resp.json())
    .then(destinations => {
      destinations.forEach(destination => {
        const groupEl = document.createElement('option');
        groupEl.value = `${destination.type}.${destination.key}`;
        groupEl.innerText = `${destination.type}: ${destination.name}`;

        groupsEl.appendChild(groupEl);
      })
    });
}

/**
 * @param {String} html HTML representing a single element.
 * @return {Element}
 * @see https://stackoverflow.com/a/35385518
 */
function htmlToElement(html) {
  const template = document.createElement('template');
  html = html.trim(); // Never return a text node of whitespace as the result
  template.innerHTML = html;
  return template.content.firstChild;
}

/**
 * @param {String} html HTML representing any number of sibling elements.
 * @return {NodeList}
 * @see https://stackoverflow.com/a/35385518
 */
function htmlToElements(html) {
  const template = document.createElement('template');
  template.innerHTML = html;
  return template.content.childNodes;
}
