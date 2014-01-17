class @SmartBootIrPanel extends SimplePanel
  bindDom: ->
    super()
    @smartSwitch = @element.find('#smart-switch')  

  enhancePage: ->
    super()
    @smartSwitch.change @toggle

  toggle: =>
    if @smartSwitch.is(':checked')
      @startSwitch()
    else
      @stopSwitch()

  startSwitch: ->
    stopSwitch() if @switchId?
    
    @switchId = setInterval(@switchRoutine, 1500)

  stopSwitch: ->
    clearInterval @switchId

  switchRoutine: =>
    @switchState = !@switchState
    
    if(@switchState)
      @sendIrCommand @commands.program_up
    else
      @sendIrCommand @commands.program_down

