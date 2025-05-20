(function() {
    // Инициализация canvas
    const canvas = document.getElementById('canvas1');
    canvas.width = 800;
    canvas.height = 600;

    const ctx = canvas.getContext('2d');

    // Состояние редактора
    const state = {
        isDrawing: false,
        startX: 0,
        startY: 0,
        mode: 'cda',
        lines: [],
        debugMode: false,
        currentStep: 0,
        interval: null
    };

    // Функция для рисования отрезка
    function drawLine(startX, startY, endX, endY) {
        if (state.mode === 'cda') {
            const dx = endX - startX;
            const dy = endY - startY;
            const steps = Math.max(Math.abs(dx), Math.abs(dy));
            const xIncrement = dx / steps;
            const yIncrement = dy / steps;

            let currentX = startX;
            let currentY = startY;

            if (state.debugMode) {
                clearInterval(state.interval);
                state.currentStep = 0;

                state.interval = setInterval(() => {
                    if (state.currentStep <= steps) {
                        ctx.fillRect(Math.round(currentX), Math.round(currentY), 1, 1);
                        currentX += xIncrement;
                        currentY += yIncrement;
                        state.currentStep++;
                    } else {
                        clearInterval(state.interval);
                        state.currentStep = 0;
                    }
                }, 100);
            } else {
                for (let i = 0; i <= steps; i++) {
                    ctx.fillRect(Math.round(currentX), Math.round(currentY), 1, 1);
                    currentX += xIncrement;
                    currentY += yIncrement;
                }
            }
            return;
        }

    if (mode === 'bresenham') {
        const dx = Math.abs(endX - startX);
        const dy = Math.abs(endY - startY);
        const sx = startX < endX ? 1 : -1;
        const sy = startY < endY ? 1 : -1;
        let err = dx - dy;

        if (debugMode) {
            // Режим отладки: отрисовка по шагам
            interval = setInterval(() => {
                if (startX !== endX || startY !== endY) {
                    ctx.fillRect(startX, startY, 1, 1);
                    const err2 = err * 2;

                    if (err2 > -dy) {
                        err -= dy;
                        startX += sx;
                    }
                    if (err2 < dx) {
                        err += dx;
                        startY += sy;
                    }
                } else {
                    ctx.fillRect(endX, endY, 1, 1);
                    clearInterval(interval);
                }
            }, 100); // Интервал в миллисекундах
        } else {
            // Обычный режим отрисовки
            while (startX !== endX || startY !== endY) {
                ctx.fillRect(startX, startY, 1, 1);
                const err2 = err * 2;

                if (err2 > -dy) {
                    err -= dy;
                    startX += sx;
                }
                if (err2 < dx) {
                    err += dx;
                    startY += sy;
                }
            }
            // Рисуем последнюю точку (x2, y2)
            ctx.fillRect(endX, endY, 1, 1);
        }
        return;
    }

    if (mode === 'wu') {
        let y2 = endY;
        let y1 = startY;
        let x1 = startX;
        let x2 = endX;
        const dx = x2 - x1;
        const dy = y2 - y1;
        const gradient = dy / dx;

        let x = x1;
        let y = y1;

        if (debugMode) {
            // Режим отладки: отрисовка по шагам
            ctx.fillRect(x, y, 1, 1); // Рисуем первую конечную точку
            interval = setInterval(() => {
                if (x < x2) {
                    ctx.fillRect(x, Math.floor(y), 1, 1);
                    ctx.fillRect(x, Math.ceil(y), 1, 1);
                    x++;
                    y += gradient;
                } else {
                    clearInterval(interval);
                }
            }, 100); // Интервал в миллисекундах
        } else {
            // Обычный режим отрисовки
            ctx.fillRect(x, y, 1, 1); // Рисуем первую конечную точку
            if (Math.abs(gradient) <= 1) {
                const yEnd = y2 < y1 ? y1 - 1 : y1 + 1;
                let intery = y + gradient;

                for (let x = x1 + 1; x <= x2 - 1; x++) {
                    ctx.fillRect(x, Math.floor(intery), 1, 1);
                    ctx.fillRect(x, Math.floor(intery) + 1, 1, 1);
                    intery += gradient;
                }
                // Рисуем вторую конечную точку
                ctx.fillRect(x2, yEnd, 1, 1);
            } else {
                const xEnd = x2 < x1 ? x1 - 1 : x1 + 1;
                let interx = x + (1 / gradient);

                for (let y = y1 + 1; y <= y2 - 1; y++) {
                    ctx.fillRect(Math.floor(interx), y, 1, 1);
                    ctx.fillRect(Math.floor(interx) + 1, y, 1, 1);
                    interx += (1 / gradient);
                }
                // Рисуем вторую конечную точку
                ctx.fillRect(xEnd, y2, 1, 1);
            }
        }
        return;
    }

    ctx.beginPath();
    ctx.moveTo(startX, startY);
    ctx.lineTo(endX, endY);
    ctx.stroke();
}

// Обработчики событий для рисования отрезков
    function handleMouseDown(e) {
        if (!state.isDrawing) {
            state.startX = e.offsetX;
            state.startY = e.offsetY;
            state.isDrawing = true;
        } else {
            const endX = e.offsetX;
            const endY = e.offsetY;
            drawLine(state.startX, state.startY, endX, endY);

            state.lines.push({
                x1: state.startX,
                y1: state.startY,
                x2: endX,
                y2: endY
            });

            state.isDrawing = false;
        }
    }

    function handleKeyDown(e) {
        if (e.key === 'd') {
            state.debugMode = !state.debugMode;
            console.log('Debug mode:', state.debugMode);
        } else if (e.key === '1' && state.debugMode) {
            if (state.lines.length > 0) {
                const lastLine = state.lines[state.lines.length - 1];
                drawLine(lastLine.x1, lastLine.y1, lastLine.x2, lastLine.y2);
            }
        }
    }

    // Публичные методы
    window.Editor1 = {
        clearCanvas: function() {
            state.lines = [];
            ctx.clearRect(0, 0, canvas.width, canvas.height);
        },

        setAlgorithm: function(alg) {
            state.mode = alg;
        },

        toggleDebugMode: function() {
            state.debugMode = !state.debugMode;
        },

        init: function() {
            canvas.addEventListener('mousedown', handleMouseDown);
            document.addEventListener('keydown', handleKeyDown);
        }
    };

    // Инициализация
    window.Editor1.init();
})();