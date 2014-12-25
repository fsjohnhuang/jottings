'use strict';

module.exports = function(grunt) {
  // Project configuration.
  grunt.initConfig({
    jade:{
      options:{
        pretty: true
      },
      compile:{
        files:{
          'index.html': 'index.jade' 
        }
      }
    },
    connect: {
      server: {
        port: 8000,
        hostname: 'localhost',
        base: '.'
      }
    },
    watch: {
      html:{
        options:{
         livereload: true
        },
        files: '*.html'
      },
      jade: {
        files: '*.jade',
        tasks: ['jade']
      }
    }
  });

  // These plugins provide necessary tasks.
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-jade');

  // Default task.
  grunt.registerTask('default', ['jade','connect', 'watch']);
};
