(function() {
    // Инициализация canvas
    const canvas = document.getElementById('canvas3');
    canvas.width = 800;
    canvas.height = 600;
    const ctx = canvas.getContext('2d');

    // Состояние редактора
    const state = {
        points: [],
        debugMode: false,
        debugIndex: 0,
        debugTimer: null,
        curveType: 'hermite',
        curvePoints: []
    };

    // Основные функции
    function addPoint(x, y) {
        state.points.push({x, y});
        state.debugIndex = 0;
        drawCurve();
    }

    function removeLastPoint() {
        state.points.pop();
        drawCurve();
    }

    function drawCurve() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Рисуем контрольные точки
        ctx.fillStyle = 'red';
        state.points.forEach(p => {
            ctx.beginPath();
            ctx.arc(p.x, p.y, 3, 0, 2 * Math.PI);
            ctx.fill();
        });

        // Рисуем кривую
        ctx.strokeStyle = 'blue';
        ctx.beginPath();

        if (state.curveType === 'hermite') {
            // Кривая Эрмита
            for (let i = 0; i < state.points.length - 1; i++) {
                const p0 = state.points[i];
                const p1 = state.points[i + 1];
                const t0 = i > 0 ? (p1.x - state.points[i - 1].x) / 2 : 0;
                const t1 = i < state.points.length - 2 ? (state.points[i + 2].x - p0.x) / 2 : 0;

                for (let t = 0; t <= 1; t += 0.01) {
                    if (state.debugMode && t * 100 > state.debugIndex) break;
                    const h1 = 2 * t ** 3 - 3 * t ** 2 + 1;
                    const h2 = -2 * t ** 3 + 3 * t ** 2;
                    const h3 = t ** 3 - 2 * t ** 2 + t;
                    const h4 = t ** 3 - t ** 2;
                    const x = h1 * p0.x + h2 * p1.x + h3 * t0 + h4 * t1;
                    const y = h1 * p0.y + h2 * p1.y + h3 * t0 + h4 * t1;
                    t === 0 ? ctx.moveTo(x, y) : ctx.lineTo(x, y);
                }
            }
        } else if (state.curveType === 'bezier') {
            if (state.points.length >= 4 && (state.points.length - 1) % 3 === 0) {
                for (let i = 0; i < state.points.length - 1; i += 3) {
                    const [p0, p1, p2, p3] = state.points.slice(i, i + 4);
                    for (let j = 0; j <= 100; j++) {
                        if (state.debugMode && j > state.debugIndex) break;
                        const t = j / 100;
                        const x = (1 - t) ** 3 * p0.x + 3 * (1 - t) ** 2 * t * p1.x + 3 * (1 - t) * t ** 2 * p2.x + t ** 3 * p3.x;
                        const y = (1 - t) ** 3 * p0.y + 3 * (1 - t) ** 2 * t * p1.y + 3 * (1 - t) * t ** 2 * p2.y + t ** 3 * p3.y;
                        j === 0 ? ctx.moveTo(x, y) : ctx.lineTo(x, y);
                    }
                }
            }
        } else if (state.curveType === 'bspline') {
            // B-сплайн
            if (state.points.length >= 4) {
                const degree = 3;
                const numPoints = state.points.length;
                const numSegments = numPoints - degree;

                // Узловой вектор (равномерный)
                const knots = [];
                for (let i = 0; i < numPoints + degree + 1; i++) {
                    knots.push(i);
                }

                // Функция для вычисления базисной функции B-сплайна
                function basisFunction(i, k, t, knots) {
                    if (k === 0) {
                        return (t >= knots[i] && t < knots[i + 1]) ? 1 : 0;
                    }
                    let denom1 = knots[i + k] - knots[i];
                    let denom2 = knots[i + k + 1] - knots[i + 1];
                    let term1 = (denom1 !== 0) ? ((t - knots[i]) / denom1) * basisFunction(i, k - 1, t, knots) : 0;
                    let term2 = (denom2 !== 0) ? ((knots[i + k + 1] - t) / denom2) * basisFunction(i + 1, k - 1, t, knots) : 0;
                    return term1 + term2;
                }

                // Построение кривой
                for (let seg = 0; seg < numSegments; seg++) {
                    for (let j = 0; j <= 100; j++) {
                        if (state.debugMode && j > state.debugIndex) break;
                        const t = knots[seg + degree] + (knots[seg + degree + 1] - knots[seg + degree]) * (j / 100);

                        let x = 0, y = 0;
                        for (let i = 0; i < numPoints; i++) {
                            const basis = basisFunction(i, degree, t, knots);
                            x += state.points[i].x * basis;
                            y += state.points[i].y * basis;
                        }

                        j === 0 ? ctx.moveTo(x, y) : ctx.lineTo(x, y);
                    }
                }
            }
        }

        ctx.stroke();
    }

    function stepDebug() {
        if (state.debugIndex < 100) {
            state.debugIndex++;
            drawCurve();
        }
    }

    function startAutoDebug() {
        if (state.debugTimer) clearInterval(state.debugTimer);
        state.debugTimer = setInterval(() => {
            if (state.debugIndex < 100) {
                state.debugIndex++;
                drawCurve();
            } else {
                clearInterval(state.debugTimer);
            }
        }, 50);
    }

    // Обработчики событий
    function handleClick(e) {
        const rect = canvas.getBoundingClientRect();
        addPoint(e.clientX - rect.left, e.clientY - rect.top);
    }

    function handleContextMenu(e) {
        e.preventDefault();
        removeLastPoint();
    }

    // Публичные методы
    window.Editor3 = {
        setCurveType: function (type) {
            state.curveType = type;
            state.debugIndex = 0;
            drawCurve();
        },

        toggleDebugMode: function () {
            state.debugMode = !state.debugMode;
            state.debugIndex = 0;
            clearInterval(state.debugTimer);
            drawCurve();
        },

        toggleAutoDebug: function () {
            if (state.debugTimer) {
                clearInterval(state.debugTimer);
                state.debugTimer = null;
            } else {
                startAutoDebug();
            }
        },

        stepDebug: function () {
            stepDebug();
        },

        clearCanvas: function () {
            state.points = [];
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            state.debugIndex = 0;
            clearInterval(state.debugTimer);
        },

        init: function () {
            canvas.addEventListener('click', handleClick);
            canvas.addEventListener('contextmenu', handleContextMenu);
        }
    };


    window.Editor3.init();
})();
