envs = 
  android:
    basePath: 'file:///android_asset'
  test:
    basePath: null

loadEnv = (env) ->
  envs[env] ? envs['android']

module.exports = (grunt) ->  
  env = loadEnv(process.env.ENV)

  grunt.initConfig
    pkg: grunt.file.readJSON('package.json')    
    clean:
      all:
        src: ['assets']
      
      skeleton:
        src: ['assets/js','assets/css','assets/fonts']

      panels:
        src: ['assets/panels/']

    copy: 
      skeleton:        
        cwd: 'assets-src/libs/'    
        src: [ '**' ]
        dest: 'assets'
        expand: true                        
        
      panels: 
        cwd: 'assets-src/panels/'    
        src: [ '!**/*.stylus', '!**/*.coffee', '!**/*.jade' ]
        dest: 'assets/panels/'
        expand: true   
    
    stylus:
      options:
        linenos: true
        compress: false
 
      skeleton:
        expand: true
        cwd: 'assets-src/css/'
        src: ['*.stylus']
        dest: 'assets/css/'
        ext: '.css'

      panels:
        expand: true
        cwd: 'assets-src/panels/'
        src: [ '**/*.styl' ],
        dest: 'assets/panels/',
        ext: '.css'    

    less:
      options:
        paths: ['assets-src/css/imports/']

      skeleton:
        expand: true
        cwd: 'assets-src/css'
        src: ['*.less']
        dest: 'assets/css'        
        ext: '.css'

    jade:    
      options:
        pretty: true
        data:
          basePath: env.basePath

      panels:
        expand: true 
        cwd: 'assets-src/panels' 
        src: [ '**/*.jade' ] 
        dest: 'assets/panels' 
        ext: '.html'

    coffee: 
      skeleton:
        expand: true
        cwd: 'assets-src/js/'
        src: ['**.coffee']
        dest: 'assets/js/'
        ext: '.js'

      panels:         
        expand: true
        cwd: 'assets-src/panels/'
        src: [ '**/*.coffee' ]
        dest: 'assets/panels/'
        ext: '.js'

    watch:
      stylus:
        files: 'assets-src/panels/**/*.styl'
        tasks: [ 'stylus:panels']
      
      scripts:
        files: 'assets-src/panels/**/*.coffee'
        tasks: [ 'coffee:panels' ]
      
      jade:
        files: 'assets-src/panels/**/*.jade'
        tasks: [ 'jade:panels' ]
      
      copy: 
        files: [ 'assets-src/panels/**', '!assets-src/panels/**/*.styl', '!assets-src/panels/**/*.coffee', '!assets-src/panels/**/*.jade' ]
        tasks: [ 'copy:panels' ]
  
      skeleton_less:
        files: 'assets-src/css/**.less'
        tasks: ['less:skeleton']

      skeleton_stylus:
        files: 'assets-src/css/**.stylus'
        tasks: ['stylus:skeleton']
      
      skeleton_scripts:
        files: 'assets-src/js/**.coffee'
        tasks: ['coffee:skeleton']
      
      skeleton_copy:
        files: ['assets-src/libs/**','assets-src/js/*.js', 'assets-src/css/*.css', '!**.less', '!**.stylus','!**.coffee']
        tasks: ['copy:skeleton']

    connect:
      server:
        options:
          port: 4000
          base: 'assets'
          hostname: '*'
        
  grunt.loadNpmTasks 'grunt-contrib-clean'
  grunt.loadNpmTasks 'grunt-contrib-copy'
  grunt.loadNpmTasks 'grunt-contrib-stylus'
  grunt.loadNpmTasks 'grunt-contrib-jade'
  grunt.loadNpmTasks 'grunt-contrib-coffee' 
  grunt.loadNpmTasks 'grunt-contrib-watch'
  grunt.loadNpmTasks 'grunt-contrib-connect'
  grunt.loadNpmTasks 'grunt-contrib-less'

  grunt.registerTask 'skeleton', 'Build skeleton resources for panels', [ 'clean:skeleton', 'copy:skeleton', 'stylus:skeleton', 'less:skeleton', 'coffee:skeleton']

  grunt.registerTask 'panels', 'Compile panels', [ 'clean:panels', 'copy:panels', 'stylus:panels', 'coffee:panels', 'jade:panels' ]
  grunt.registerTask 'build', 'Build panels with all resources', [ 'clean:all', 'skeleton', 'panels']  
  grunt.registerTask 'default', ['build']
  grunt.registerTask 'dev', "Development mode", ['build', 'connect', 'watch']

