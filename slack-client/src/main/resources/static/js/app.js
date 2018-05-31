let stompClient = null;

document.addEventListener('DOMContentLoaded', () => {
  const messagesEl = document.querySelector('#messages');
  messagesEl.scrollTop = messagesEl.scrollHeight - messagesEl.offsetHeight;

  connect();
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

function addMessage(message) {
  const messagesEl = document.querySelector('#messages');

  const scrolledToBottom = messagesEl.scrollTop ===
    (messagesEl.scrollHeight - messagesEl.offsetHeight);

  const timestampEl = document.createElement('span');
  timestampEl.classList.add('message__timestamp');
  timestampEl.setAttribute('data-livestamp', message.date);
  timestampEl.innerHTML = '&nbsp;';

  const bodyEl = document.createElement('span');
  timestampEl.classList.add('message__content');
  bodyEl.innerText = message.message;

  const rowEl = document.createElement('div');
  rowEl.classList.add('message', 'message--received');
  rowEl.appendChild(bodyEl);
  rowEl.appendChild(timestampEl);

  messagesEl.appendChild(rowEl);

  if (scrolledToBottom) {
    rowEl.scrollIntoView({
      behavior: 'smooth',
      block: 'end',
      inline: 'nearest',
    });
  }
}
