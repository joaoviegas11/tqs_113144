
         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: k6-thymeleaf-test.js
        output: -

     scenarios: (100.00%) 1 scenario, 10 max VUs, 1m0s max duration (incl. graceful stop):
              * default: 10 looping VUs for 30s (gracefulStop: 30s)



  █ TOTAL RESULTS

    checks_total.......................: 1500    49.625025/s
    checks_succeeded...................: 100.00% 1500 out of 1500
    checks_failed......................: 0.00%   0 out of 1500

    ✓ GET /site/refectories status 200
    ✓ GET /site/menus?refectoryId=1 status 200
    ✓ POST /site/reservations status 200
    ✓ POST /site/reservations/search status 200
    ✓ POST /site/reservations/cancel status 200
    ✓ POST /site/reservations/checkin status 200

    HTTP
    http_req_duration.......................................................: avg=33.87ms min=10.85ms med=28.2ms max=148ms p(90)=57.36ms p(95)=67.37ms
      { expected_response:true }............................................: avg=33.87ms min=10.85ms med=28.2ms max=148ms p(90)=57.36ms p(95)=67.37ms
    http_req_failed.........................................................: 0.00%  0 out of 1500
    http_reqs...............................................................: 1500   49.625025/s

    EXECUTION
    iteration_duration......................................................: avg=1.2s    min=1.12s   med=1.2s   max=1.4s  p(90)=1.26s   p(95)=1.34s
    iterations..............................................................: 250    8.270837/s
    vus.....................................................................: 10     min=10        max=10
    vus_max.................................................................: 10     min=10        max=10

    NETWORK
    data_received...........................................................: 5.2 MB 171 kB/s
    data_sent...............................................................: 235 kB 7.8 kB/s



                                                                                                                                                
running (0m30.2s), 00/10 VUs, 250 complete and 0 interrupted iterations                                                                         
default ✓ [======================================] 10 VUs  30s                             