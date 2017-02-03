new Vue({
  el: '#app',
  data: {
    progress: 0
  },
  methods: {
    start () {
      //let csrfHeaderName = document.getElementsByName('_csrf_header')[0].getAttribute('content')
      //let csrfToken = document.getElementsByName('_csrf')[0].getAttribute('content')
      //let headers = new Headers()
      //headers.append(csrfHeaderName, csrfToken)
      let req = new Request('run', { method: 'POST'/*, headers */ })
      fetch(req)
        .then(res => res.text())
        .then(name => {
          let es = new EventSource('progress/' + name)
          es.onmessage = e => {
            let value = parseFloat(e.data)
            TweenLite.to(this, 1, { progress: value * 100 })
            if (value >= 1) {
              es.close()
            }
          }
        })
    }
  }
})
