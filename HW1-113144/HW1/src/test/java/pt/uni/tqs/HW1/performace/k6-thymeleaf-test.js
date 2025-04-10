import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 20,
    duration: '30s',
};

const BASE_URL = 'http://localhost:8080';

export default function () {
    let res1 = http.get(`${BASE_URL}/site/refectories`);
    check(res1, {
        'GET /site/refectories status 200': (r) => r.status === 200,
    });

    let res2 = http.get(`${BASE_URL}/site/menus?refectoryId=1`);
    check(res2, {
        'GET /site/menus?refectoryId=1 status 200': (r) => r.status === 200,
    });

    let res3 = http.post(`${BASE_URL}/site/reservations`, { menuId: 1 });
    check(res3, {
        'POST /site/reservations status 200': (r) => r.status === 200,
    });

    let token = 'abc';

    let res4 = http.post(`${BASE_URL}/site/reservations/search`, { token });
    check(res4, {
        'POST /site/reservations/search status 200': (r) => r.status === 200,
    });

    let res5 = http.post(`${BASE_URL}/site/reservations/cancel`, { token });
    check(res5, {
        'POST /site/reservations/cancel status 200': (r) => r.status === 200,
    });

    let res6 = http.post(`${BASE_URL}/site/reservations/checkin`, { token });
    check(res6, {
        'POST /site/reservations/checkin status 200': (r) => r.status === 200,
    });

    sleep(1);
}
