(function() {
    // Инициализация canvas
    const canvas = document.getElementById('canvas2');
    canvas.width = 800;
    canvas.height = 600;

    const ctx = canvas.getContext('2d');

    // Состояние редактора
    const state = {
        points: [],
        debugMode: false,
        autoDebug: false,
        curvePoints: [],
        drawIndex: 0,
        timer: null,
        curveType: 'circle'
    };

    // Основные функции
    function selectPoint(e) {
        const x = e.offsetX;
        const y = e.offsetY;
        state.points.push({ x, y });

        if (state.points.length === 2) {
            drawCurve(state.debugMode);
            state.points = [];
        }
    }

    function drawCurve(debug = false) {
        const centerX = state.points[0].x;
        const centerY = state.points[0].y;
        const a = Math.abs(state.points[1].x - centerX);
        const b = Math.abs(state.points[1].y - centerY);
        const step = 1 / Math.max(a, b);

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        state.curvePoints = [];
        state.drawIndex = 0;

        for (let angle = -2 * Math.PI; angle < 2 * Math.PI; angle += step) {
            let x, y;

            switch (state.curveType) {
                case 'circle':
                    x = centerX + a * Math.cos(angle);
                    y = centerY + a * Math.sin(angle);
                    break;
                case 'ellipse':
                    x = centerX + a * Math.cos(angle);
                    y = centerY + b * Math.sin(angle);
                    break;
                case 'hyperbola':
                    x = centerX + a / Math.cos(angle);
                    y = centerY + b * Math.tan(angle);
                    break;
                case 'parabola':
                    const direction = state.points[1].y > centerY ? 1 : -1;
                    x = centerX + a * angle;
                    y = centerY + direction * b * Math.pow(angle, 2);
                    break;
            }

            state.curvePoints.push({ x, y });
        }

        if (debug) {
            if (state.autoDebug) startAutoDraw();
        } else {
            drawFullCurve();
        }
    }

    function drawFullCurve() {
        ctx.beginPath();
        state.curvePoints.forEach(point => {
            ctx.fillRect(point.x, point.y, 1, 1);
        });
    }

    function startAutoDraw() {
        clearInterval(state.timer);
        state.timer = setInterval(drawNextPixel, 40);
    }

    function drawNextPixel() {
        if (state.drawIndex < state.curvePoints.length) {
            const point = state.curvePoints[state.drawIndex];
            ctx.fillRect(point.x, point.y, 1, 1);
            state.drawIndex++;
        } else {
            clearInterval(state.timer);
        }
    }

    // Обработчики событий
    function handleKeyPress(e) {
        if (state.debugMode && e.code === 'Space' && state.drawIndex < state.curvePoints.length) {
            drawNextPixel();
        }
    }

    // Публичные методы
    window.Editor2 = {
        clearCanvas: function() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            state.points = [];
            state.curvePoints = [];
            state.drawIndex = 0;
            clearInterval(state.timer);
        },

        toggleDebugMode: function() {
            state.debugMode = !state.debugMode;
            console.log('Debug mode:', state.debugMode);
        },

        toggleAutoDebug: function() {
            state.autoDebug = !state.autoDebug;
            console.log('Auto debug:', state.autoDebug);
        },

        setCurveType: function(type) {
            state.curveType = type;
        },

        init: function() {
            canvas.addEventListener('click', selectPoint);
            document.addEventListener('keydown', handleKeyPress);
        }
    };

    // Инициализация
    window.Editor2.init();
})();