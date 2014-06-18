envs = 
  android:
    basePath: 'file:///android_asset/'
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
        src: ['build/assets']
      
      skeleton:
        src: ['build/assets/js','build/assets/css','build/assets/fonts']

      panels:
        src: ['build/assets/panels/']

    copy: 
      skeleton:        
        cwd: 'src/main/assets/libs/'    
        src: [ '**' ]
        dest: 'build/assets'
        expand: true                        
        
      panels: 
        cwd: 'src/main/assets/panels/'    
        src: [ '**/*', '!**/*.stylus', '!**/*.coffee', '!**/*.jade' ]
        dest: 'build/assets/panels/'
        expand: true   
    
    stylus:
      options:
        linenos: true
        compress: false
 
      skeleton:
        expand: true
        cwd: 'src/main/assets/css/'
        src: ['*.stylus']
        dest: 'build/assets/css/'
        ext: '.css'

      panels:
        expand: true
        cwd: 'src/main/assets/panels/'
        src: [ '**/*.styl' ],
        dest: 'build/assets/panels/',
        ext: '.css'    

    less:
      options:
        paths: ['src/main/assets/css/imports/']

      skeleton:
        expand: true
        cwd: 'src/main/assets/css'
        src: ['*.less']
        dest: 'build/assets/css'        
        ext: '.css'

    jade:    
      options:
        pretty: true
        data:
          basePath: env.basePath

      panels:
        expand: true 
        cwd: 'src/main/assets/panels' 
        src: [ '**/*.jade' ] 
        dest: 'build/assets/panels' 
        ext: '.html'

    coffee: 
      skeleton:
        expand: true
        cwd: 'src/main/assets/js/'
        src: ['**.coffee']
        dest: 'build/assets/js/'
        ext: '.js'

      panels:         
        expand: true
        cwd: 'src/main/assets/panels/'
        src: [ '**/*.coffee' ]
        dest: 'build/assets/panels/'
        ext: '.js'

    watch:
      stylus:
        files: 'src/main/assets/panels/**/*.styl'
        tasks: [ 'stylus:panels']
      
      scripts:
        files: 'src/main/assets/panels/**/*.coffee'
        tasks: [ 'coffee:panels' ]
      
      jade:
        files: 'src/main/assets/panels/**/*.jade'
        tasks: [ 'jade:panels' ]
      
      copy: 
        files: [ 'src/main/assets/panels/**', '!src/main/assets/panels/**/*.styl', '!src/main/assets/panels/**/*.coffee', '!src/main/assets/panels/**/*.jade' ]
        tasks: [ 'copy:panels' ]
  
      skeleton_less:
        files: 'src/main/assets/css/**.less'
        tasks: ['less:skeleton']

      skeleton_stylus:
        files: 'src/main/assets/css/**.stylus'
        tasks: ['stylus:skeleton']
      
      skeleton_scripts:
        files: 'src/main/assets/js/**.coffee'
        tasks: ['coffee:skeleton']
      
      skeleton_copy:
        files: ['src/main/assets/libs/**','src/main/assets/js/*.js', 'src/main/assets/css/*.css', '!**.less', '!**.stylus','!**.coffee']
        tasks: ['copy:skeleton']

    connect:
      server:
        options:
          port: 4000
          base: 'build/assets'
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

