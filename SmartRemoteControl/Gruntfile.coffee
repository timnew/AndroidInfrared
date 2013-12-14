module.exports = (grunt) ->
  
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
            src: [ '!{js,css}' ]
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
        data: {}        

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
        
  grunt.loadNpmTasks 'grunt-contrib-clean'
  grunt.loadNpmTasks 'grunt-contrib-copy'
  grunt.loadNpmTasks 'grunt-contrib-stylus'
  grunt.loadNpmTasks 'grunt-autoprefixer'
  grunt.loadNpmTasks 'grunt-contrib-cssmin'  
  grunt.loadNpmTasks 'grunt-contrib-jade'
  grunt.loadNpmTasks 'grunt-contrib-coffee' 
  grunt.loadNpmTasks 'grunt-contrib-uglify'

  grunt.registerTask 'skeleton', 'Build skeleton resources for panels', [ 'clean:skeleton', 'copy:skeleton', 'stylus:skeleton', 'autoprefixer:skeleton', 'cssmin:skeleton', 'coffee:skeleton', 'uglify:skeleton', 'clean:skeleton_merge' ]
  grunt.registerTask 'panels', 'Compile panels', [ 'clean:panels', 'copy:panels', 'stylus:panels', 'autoprefixer:panels', 'coffee:panels', 'jade:panels' ]
  grunt.registerTask 'build', 'Build panels with all resources', [ 'clean:all', 'skeleton', 'panels']  
  grunt.registerTask 'default', ['build']

