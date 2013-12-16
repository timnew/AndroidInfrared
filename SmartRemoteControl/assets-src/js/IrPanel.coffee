class @IrPanel extends Widget
  bindDom: ->

  enhancePage: ->  
    @element.find('[data-ir-type]').click @onIrButtonClicked

  onIrButtonClicked: (e) =>
    $button = $(e.target)
    type = $button.data('irType')    
    sendFunc = ir[type]

    code = @parseCode($button.data('irCode'))

    if(type is 'Panasonic')
      address = @parseCode($button.data('address'))
      sendFunc(address, code)
    else
      length = $button.data('irLength')
      sendFunc(length, code)

  parseCode: (rawCode) ->
    return rawCode if typeof(rawCode) is 'number'
    parseInt(rawCode, 16)


