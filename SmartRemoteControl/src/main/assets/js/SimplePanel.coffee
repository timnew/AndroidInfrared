class @SimplePanel extends Panel
  enhancePage: ->
    @element.find('[data-ir]').click @onIrButtonClicked

  initialize: ->
    @loadJson @element.data('irMapping'), (data) =>
        @commands = data

  onIrButtonClicked: (e) =>
    e.preventDefault()

    $button = $(e.currentTarget)
    commandName = $button.data('ir')    
    
    command = @commands[commandName]

    @sendIrCommand command