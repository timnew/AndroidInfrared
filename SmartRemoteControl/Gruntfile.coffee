buildPathHelper = ->
  path = require('path')
  rootPath = path.join.apply(path, arguments)
  ->
    joinedRelativePath = path.join.apply(path, arguments)
    path.join(rootPath, joinedRelativePath)

envs = 
  android:
    rootPath: 'file:///android_asset'
  test:
    rootPath: '/'

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

      skeleton_merge:        
        src: ['assets/js/merge','assets/css/merge']

      panels:
        src: ['assets/panels/']

    copy: 
      skeleton:
        files: [{
            cwd: 'assets-src/libs/'    
            src: [ '**', '!*.js', '!*.css' ]
            dest: 'assets'
            expand: true              
          },{
            cwd: 'assets-src/libs/js'    
            src: [ '*.js', '**.js' ]
            dest: 'assets/js/merge'
            expand: true              
          },{
            cwd: 'assets-src/libs/css'    
            src: [ '*.css', '**.css' ]
            dest: 'assets/css/merge'
            expand: true              
          }]
        
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
        src: ['**.stylus']
        dest: 'assets/css/merge'
        ext: '.css'

      panels:
        expand: true
        cwd: 'assets-src/panels/'
        src: [ '**/*.styl' ],
        dest: 'assets/panels/',
        ext: '.css'    

    autoprefixer:
      skeleton:
        expand: true
        cwd: 'assets/css/merge'
        src:['**.css']
        dest: 'assets/css/merge'

      panels:
        expand: true
        cwd: 'assets/panels/'
        src: [ '**/*.css' ]
        dest: 'assets/panels/'

    cssmin:
      skeleton:       
        files:
          'assets/css/skeleton.css': ['assets/css/merge/*.css','assets/css/merge/**/*.css']        

    jade:    
      options:
        data:
          root: buildPathHelper(env.rootPath)
          js: buildPathHelper(env.rootPath, 'js')
          css: buildPathHelper(env.rootPath, 'css')

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
        dest: 'assets/js/merge'
        ext: '.js'

      panels:         
        expand: true
        cwd: 'assets-src/panels/'
        src: [ '**/*.coffee' ]
        dest: 'assets/panels/'
        ext: '.js'

    uglify:
      options:
          mangle: false    
      skeleton:
        files:
          'assets/js/skeleton.js': ['assets/js/merge/*.js','assets/js/merge/**/*.js']        

    concat:
      js:
        options:
          separator: ';'
        files:
          'assets/js/skeleton.js': ['assets/js/merge/*.js','assets/js/merge/**/*.js']
      css:
        files:
          'assets/css/skeleton.css': ['assets/css/merge/*.css','assets/css/merge/**/*.css']

    watch:
      stylesheets:
        files: 'assets-src/panels/**/*.styl'
        tasks: [ 'stylus:panels', 'autoprefixer:panels' ]
      
      scripts:
        files: 'assets-src/panels/**/*.coffee'
        tasks: [ 'coffee:panels' ]
      
      jade:
        files: 'assets-src/panels/**/*.jade'
        tasks: [ 'jade:panels' ]
      
      copy: 
        files: [ 'assets-src/panels/**', '!assets-src/panels/**/*.styl', '!assets-src/panels/**/*.coffee', '!assets-src/panels/**/*.jade' ]
        tasks: [ 'copy:panels' ]
  
      skeleton_stylesheets:
        files: 'assets-src/css/*.stylus'
        tasks: ['copy:skeleton', 'stylus:skeleton', 'cssmin:skeleton', 'clean:skeleton_merge']
      
      skeleton_scripts:
        files: 'assets-src/js/*.coffee'
        tasks: ['copy:skeleton', 'coffee:skeleton', 'uglify:skeleton', 'clean:skeleton_merge']
      
      skeleton_copy:
        files: ['assets-src/libs/**','assets-src/js/*.js', 'assets-src/css/*.css', '!**.stylus','!**.coffee']
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
  grunt.loadNpmTasks 'grunt-autoprefixer'
  grunt.loadNpmTasks 'grunt-contrib-cssmin'  
  grunt.loadNpmTasks 'grunt-contrib-jade'
  grunt.loadNpmTasks 'grunt-contrib-coffee' 
  grunt.loadNpmTasks 'grunt-contrib-uglify'
  grunt.loadNpmTasks 'grunt-contrib-watch'
  grunt.loadNpmTasks 'grunt-contrib-concat'
  grunt.loadNpmTasks 'grunt-contrib-connect'

  grunt.registerTask 'skeleton', 'Build skeleton resources for panels', [ 'clean:skeleton', 'copy:skeleton', 'stylus:skeleton', 'concat:css', 'coffee:skeleton', 'concat:js', 'clean:skeleton_merge' ]

  grunt.registerTask 'panels', 'Compile panels', [ 'clean:panels', 'copy:panels', 'stylus:panels', 'autoprefixer:panels', 'coffee:panels', 'jade:panels' ]
  grunt.registerTask 'build', 'Build panels with all resources', [ 'clean:all', 'skeleton', 'panels']  
  grunt.registerTask 'default', ['build']
  grunt.registerTask 'dev', "Development mode", ['build', 'connect', 'watch']

