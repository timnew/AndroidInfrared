class @IrPanel extends Widget
  bindDom: ->

  enhancePage: ->  
    @element.find('[data-ir-type]').click @onIrButtonClicked

  onIrButtonClicked: (e) =>
    e.preventDefault()
    $button = $(e.currentTarget)
    type = $button.data('irType')    
    
    sendFunc = ir[type]

    code = @parseCode($button.data('irCode'))

    if(type is 'Panasonic')
      address = @parseCode($button.data('address'))      
      sendFunc.call(ir, address, code)
    else
      length = $button.data('irLength')      
      sendFunc.call(ir, length, code)


  parseCode: (rawCode) ->
    return rawCode if typeof(rawCode) is 'number'
    parseInt(rawCode, 16)


