class @Panel extends Widget

  resolveUrl: (file) ->
    fullPath = location.pathname;
    dividerPos = fullPath.lastIndexOf('/');
    parentPath = fullPath.substring(0, dividerPos + 1);

    parentPath + file

  loadJson: (jsonFile, callback) ->
    url = @resolveUrl jsonFile
    $.getJSON url, (data) =>
        callback data

  sendIrCommand: (command) ->
    return unless command?

    sendFunc = ir[command.type]

    return unless sendFunc?

    if(command.type is 'Panasonic')
      sendFunc.call(ir, command.address, command.code)
    else
      sendFunc.call(ir, command.length, command.code)