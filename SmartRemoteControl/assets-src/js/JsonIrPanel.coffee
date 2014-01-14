class @JsonIrPanel extends Widget
  bindDom: ->

  enhancePage: ->  
    @element.find('[data-ir]').click @onIrButtonClicked

  initialize: ->     
    url = @resolveUrl @element.data('irMapping')
    $.getJSON url, (data) =>
      @commands = data  

  onIrButtonClicked: (e) =>
    e.preventDefault()

    $button = $(e.currentTarget)
    commandName = $button.data('ir')    
    
    command = @commands[commandName]

    @sendIrCommand command

  resolveUrl: (file) ->
    fullPath = location.pathname;
    dividerPos = fullPath.lastIndexOf('/');    
    parentPath = fullPath.substring(0, dividerPos + 1);    
    
    parentPath + file

  sendIrCommand: (command) ->
    return unless command?

    sendFunc = ir[command.type]

    return unless sendFunc?

    if(command.type is 'Panasonic')      
      sendFunc.call(ir, command.address, command.code)
    else      
      sendFunc.call(ir, command.length, command.code)