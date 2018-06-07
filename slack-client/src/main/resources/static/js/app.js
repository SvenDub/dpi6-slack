let stompClient = null;
let messageTemplate = null;
let messagesNavTemplate = null;
let messagesContentTemplate = null;

/**
 * This callback is displayed as a global member.
 * @callback callbackFn
 * @param {*} item - item
 * @return {*} key
 */

/**
 *
 * @param {callbackFn} propSelector
 * @returns {*}
 */
function groupBy(propSelector) {
  return this.reduce((groups, item) => {
    const val = propSelector(item);
    groups[val] = groups[val] || [];
    groups[val].push(item);
    return groups;
  }, {});
}

Array.prototype.groupBy = groupBy;

document.addEventListener('DOMContentLoaded', () => {
  messageTemplate = Handlebars.compile(document.getElementById('message-template').innerHTML);
  messagesNavTemplate = Handlebars.compile(document.getElementById('messages-nav-template').innerHTML);
  messagesContentTemplate = Handlebars.compile(document.getElementById('messages-content-template').innerHTML);

  connect();

  loadDestinations();
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

function onSubmitMessageForm(messageSelector, destination) {
  let message = document.querySelector(messageSelector);
  sendMessage({content: message.value}, destination);
  message.value = '';
  return false;
}

function sendMessage(message, destination) {
  stompClient.send('/app/send', {'X-Destination': destination}, JSON.stringify(message));
}

function addMessage(message) {
  let messagesEl = null;

  if (message.message.destination.startsWith('group.')) {
    messagesEl = document.querySelector(`[data-key="${message.message.destination}"]`);
  } else if (message.message.destination.startsWith('user.')) {
    if (message.type === 'sent') {
      messagesEl = document.querySelector(`[data-key="${message.message.destination}"]`);
    } else if (message.type === 'received') {
      messagesEl = document.querySelector(`[data-key="user.${message.message.sender}"]`);
    } else {
      console.error('Message type not understood', message);
      return;
    }
  } else {
    console.error('Destination not found for message', message);
    return;
  }

  messagesEl = messagesEl.firstElementChild;

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

function loadDestinations() {
  const destinationsNavEl = document.querySelector('#messages-destination');
  const destinationsContentEl = document.querySelector('#messages-content');

  fetch('/api/destinations')
    .then(resp => resp.json())
    .then(destinations => {
      const types = destinations
        .filter(destination => destination.key !== user)
        .groupBy(destination => destination.type);

      let i = 0;
      for (const type in types) {
        if (types.hasOwnProperty(type)) {
          if (i !== 0) {
            const separatorEl = document.createElement('div');
            separatorEl.classList.add('dropdown-divider');
            destinationsNavEl.appendChild(separatorEl)
          }

          for (const destination of types[type]) {
            const navElement = htmlToElement(messagesNavTemplate(destination));
            destinationsNavEl.appendChild(navElement);

            const contentElement = htmlToElement(
              messagesContentTemplate(destination));
            destinationsContentEl.appendChild(contentElement);
          }
          i++;
        }
      }
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
