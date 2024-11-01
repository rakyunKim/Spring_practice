!function (e, t) {
    "object" == typeof exports && "undefined" != typeof module ? module.exports = t(require("echarts/lib/echarts"), require("echarts/lib/component/tooltip"), require("echarts/lib/component/legend"), require("echarts/lib/chart/bar"), require("echarts/lib/chart/line"), require("echarts/lib/chart/pie"), require("echarts/lib/chart/funnel"), require("echarts/lib/chart/radar"), require("echarts/lib/chart/map"), require("echarts/extension/bmap/bmap"), require("echarts-amap"), require("echarts/lib/chart/sankey"), require("echarts/lib/chart/heatmap"), require("echarts/lib/component/visualMap"), require("echarts/lib/chart/scatter"), require("echarts/lib/chart/candlestick"), require("echarts/lib/component/dataZoom"), require("echarts/lib/chart/gauge"), require("echarts/lib/chart/tree"), require("echarts-liquidfill"), require("echarts-wordcloud")) : "function" == typeof define && define.amd ? define(["echarts/lib/echarts", "echarts/lib/component/tooltip", "echarts/lib/component/legend", "echarts/lib/chart/bar", "echarts/lib/chart/line", "echarts/lib/chart/pie", "echarts/lib/chart/funnel", "echarts/lib/chart/radar", "echarts/lib/chart/map", "echarts/extension/bmap/bmap", "echarts-amap", "echarts/lib/chart/sankey", "echarts/lib/chart/heatmap", "echarts/lib/component/visualMap", "echarts/lib/chart/scatter", "echarts/lib/chart/candlestick", "echarts/lib/component/dataZoom", "echarts/lib/chart/gauge", "echarts/lib/chart/tree", "echarts-liquidfill", "echarts-wordcloud"], t) : e.VeIndex = t(e.echarts)
}(this, function (e) {
    "use strict";
    e = e && e.hasOwnProperty("default") ? e.default : e;
    var t = {
            categoryAxis: {axisLine: {show: !1}, axisTick: {show: !1}, splitLine: {show: !1}},
            valueAxis: {axisLine: {show: !1}},
            line: {smooth: !0},
            grid: {containLabel: !0, left: 10, right: 10}
        },
        i = ["#19d4ae", "#5ab1ef", "#fa6e86", "#ffb980", "#0067a6", "#c4b4e4", "#d87a80", "#9cbbff", "#d9d0c7", "#87a997", "#d49ea2", "#5b4947", "#7ba3a8"],
        n = ["#313695", "#4575b4", "#74add1", "#abd9e9", "#e0f3f8", "#ffffbf", "#fee090", "#fdae61", "#f46d43", "#d73027", "#a50026"],
        a = ["blue", "blue", "green", "yellow", "red"], r = function (e) {
            return ['<span style="', "background-color:" + e + ";", "display: inline-block;", "width: 10px;", "height: 10px;", "border-radius: 50%;", "margin-right:2px;", '"></span>'].join("")
        }, o = ["initOptions", "loading", "dataEmpty", "judgeWidth", "widthChangeDelay"],
        s = ["grid", "dataZoom", "visualMap", "toolbox", "title", "legend", "xAxis", "yAxis", "radar", "tooltip", "axisPointer", "brush", "geo", "timeline", "graphic", "series", "backgroundColor", "textStyle"],
        l = {th: 3, mi: 6, bi: 9, tr: 12}, c = {
            zeroFormat: null,
            nullFormat: null,
            defaultFormat: "0,0",
            scalePercentBy100: !0,
            abbrLabel: {th: "k", mi: "m", bi: "b", tr: "t"}
        }, u = 1e12, d = 1e9, m = 1e6, p = 1e3;

    function h(e, t, i, n) {
        var a = e.toString().split("."), r = t - (n || 0),
            o = 2 === a.length ? Math.min(Math.max(a[1].length, r), t) : r, s = Math.pow(10, o),
            l = (i(e + "e+" + o) / s).toFixed(o);
        if (n > t - o) {
            var c = new RegExp("\\.?0{1," + (n - (t - o)) + "}$");
            l = l.replace(c, "")
        }
        return l
    }

    function f(e, t, i, n) {
        var a = Math.abs(t), r = !1, o = !1, s = "", c = "", f = !1, v = void 0, b = void 0;
        i = i || "", t = t || 0, ~i.indexOf("(") ? (r = !0, i = i.replace(/[(|)]/g, "")) : (~i.indexOf("+") || ~i.indexOf("-")) && (b = ~i.indexOf("+") ? i.indexOf("+") : t < 0 ? i.indexOf("-") : -1, i = i.replace(/[+|-]/g, "")), ~i.indexOf("a") && (v = !!(v = i.match(/a(k|m|b|t)?/)) && v[1], ~i.indexOf(" a") && (s = " "), i = i.replace(new RegExp(s + "a[kmbt]?"), ""), a >= u && !v || "t" === v ? (s += e.abbrLabel.tr, t /= u) : a < u && a >= d && !v || "b" === v ? (s += e.abbrLabel.bi, t /= d) : a < d && a >= m && !v || "m" === v ? (s += e.abbrLabel.mi, t /= m) : (a < m && a >= p && !v || "k" === v) && (s += e.abbrLabel.th, t /= p)), ~i.indexOf("[.]") && (o = !0, i = i.replace("[.]", "."));
        var y = t.toString().split(".")[0], g = i.split(".")[1], x = i.indexOf(","),
            w = (i.split(".")[0].split(",")[0].match(/0/g) || []).length;
        if (g ? (y = (c = ~g.indexOf("[") ? h(t, (g = (g = g.replace("]", "")).split("["))[0].length + g[1].length, n, g[1].length) : h(t, g.length, n)).split(".")[0], c = ~c.indexOf(".") ? "." + c.split(".")[1] : "", o && 0 == +c.slice(1) && (c = "")) : y = h(t, 0, n), s && !v && +y >= 1e3 && s !== l.trillion && (y = "" + +y / 1e3, s = l.million), ~y.indexOf("-") && (y = y.slice(1), f = !0), y.length < w) for (var S = w - y.length; S > 0; S--) y = "0" + y;
        x > -1 && (y = y.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")), i.indexOf(".") || (y = "");
        var A = y + c + (s || "");
        return r ? A = (r && f ? "(" : "") + A + (r && f ? ")" : "") : b >= 0 ? A = 0 === b ? (f ? "-" : "+") + A : A + (f ? "-" : "+") : f && (A = "-" + A), A
    }

    function v(e, t) {
        Object.keys(t).forEach(function (i) {
            e[i] = t[i]
        })
    }

    var b = {}, y = {};

    function g(e, t, i) {
        return function (e, t, i) {
            t = t || b.defaultFormat, i = i || Math.round;
            var n = void 0, a = void 0;
            if (0 === e && null !== b.zeroFormat) n = b.zeroFormat; else if (null === e && null !== b.nullFormat) n = b.nullFormat; else {
                for (var r in y) if (y[r] && t.match(y[r].regexp)) {
                    a = y[r].format;
                    break
                }
                n = (a = a || f.bind(null, b))(e, t, i, g)
            }
            return n
        }(0 === e || void 0 === e ? 0 : null === e || function (e) {
            return "number" == typeof e && isNaN(e)
        }(e) ? null : "string" == typeof e ? b.zeroFormat && e === b.zeroFormat ? 0 : b.nullFormat && e === b.nullFormat || !e.replace(/[^0-9]+/g, "").length ? null : +e : +e || null, t, i)
    }

    v(b, c), g.options = b, g._numberToFormat = f.bind(null, b), g.register = function (e, t) {
        y[e] = t
    }, g.unregister = function (e) {
        y[e] = null
    }, g.setOptions = function (e) {
        v(b, e)
    }, g.reset = function () {
        v(b, c)
    }, g.register("percentage", {
        regexp: /%/, format: function (e, t, i, n) {
            var a = ~t.indexOf(" %") ? " " : "", r = void 0;
            return n.options.scalePercentBy100 && (e *= 100), t = t.replace(/\s?%/, ""), ~(r = n._numberToFormat(e, t, i)).indexOf(")") ? ((r = r.split("")).splice(-1, 0, a + "%"), r = r.join("")) : r = r + a + "%", r
        }
    });
    var x = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (e) {
        return typeof e
    } : function (e) {
        return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
    }, w = function (e, t, i) {
        return t in e ? Object.defineProperty(e, t, {
            value: i,
            enumerable: !0,
            configurable: !0,
            writable: !0
        }) : e[t] = i, e
    }, S = Object.assign || function (e) {
        for (var t = 1; t < arguments.length; t++) {
            var i = arguments[t];
            for (var n in i) Object.prototype.hasOwnProperty.call(i, n) && (e[n] = i[n])
        }
        return e
    }, A = function (e) {
        return Array.isArray(e) ? e : Array.from(e)
    };

    function O(e, t) {
        var i = null;
        return function () {
            var n = this, a = arguments;
            clearTimeout(i), i = setTimeout(function () {
                e.apply(n, a)
            }, t)
        }
    }

    function M(e, t, i) {
        if (t) {
            var n = e, a = t.split(".");
            a.forEach(function (e, t) {
                t === a.length - 1 ? n[e] = i : (n[e] || (n[e] = {}), n = n[e])
            })
        }
    }

    var V = "function" == typeof Symbol && "symbol" === x(Symbol.iterator) ? function (e) {
        return void 0 === e ? "undefined" : x(e)
    } : function (e) {
        return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : void 0 === e ? "undefined" : x(e)
    };

    function T(e) {
        return Object.prototype.toString.call(e)
    }

    function N(e) {
        return void 0 === e ? "undefined" : V(e)
    }

    function k(e) {
        return "[object Object]" === T(e)
    }

    function L(e) {
        return "[object Array]" === T(e)
    }

    function j(e) {
        return JSON.parse(JSON.stringify(e))
    }

    function E(e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }

    var z = function (e, t, i) {
        var n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : "-";
        if (isNaN(e)) return n;
        if (!t) return e;
        if ("[object Function]" === T(t)) return t(e, g);
        i = isNaN(i) ? 0 : ++i;
        var a = ".[" + new Array(i).join(0) + "]", r = t;
        switch (t) {
            case"KMB":
                r = i ? "0,0" + a + "a" : "0,0a";
                break;
            case"normal":
                r = i ? "0,0" + a : "0,0";
                break;
            case"percent":
                r = i ? "0,0" + a + "%" : "0,0.[00]%"
        }
        return g(e, r)
    }, R = function (e) {
        var t = {};
        return Object.keys(e).forEach(function (i) {
            e[i].forEach(function (e) {
                t[e] = i
            })
        }), t
    }, C = {}, H = function (e) {
        var t, i = e.position, n = e.positionJsonLink, a = e.beforeRegisterMapOnce, r = e.mapURLProfix,
            o = n || "" + r + i + ".json";
        return C[o] || (C[o] = (t = o, new Promise(function (e, i) {
            var n = new XMLHttpRequest;
            n.open("GET", t), n.send(null), n.onload = function () {
                e(JSON.parse(n.responseText))
            }, n.onerror = function () {
                i(JSON.parse(n.responseText))
            }
        })).then(function (e) {
            return a && (e = a(e)), e
        })), C[o]
    }, F = null, _ = null, P = function (e, t) {
        return F || (F = new Promise(function (i, n) {
            var a = "bmap" + Date.now();
            window[a] = i;
            var r = document.createElement("script");
            r.src = ["https://api.map.baidu.com/api?v=" + (t || "2.0"), "ak=" + e, "callback=" + a].join("&"), document.body.appendChild(r)
        })), F
    }, D = function (e, t) {
        return _ || (_ = new Promise(function (i, n) {
            var a = "amap" + Date.now();
            window[a] = i;
            var r = document.createElement("script");
            r.src = ["https://webapi.amap.com/maps?v=" + (t || "1.4.3"), "key=" + e, "callback=" + a].join("&"), document.body.appendChild(r)
        })), _
    };

    function q(e, t, i) {
        void 0 !== e[t] ? e[t].push(i) : e[t] = [i]
    }

    var $ = .5;

    function G(e) {
        var t = e.innerRows, i = e.dimAxisName, n = e.dimension, a = e.axisVisible, r = e.dimAxisType, o = e.dims;
        return n.map(function (e) {
            return {
                type: "category", name: i, nameLocation: "middle", nameGap: 22, data: "value" === r ? function (e) {
                    for (var t = Math.max.apply(null, e), i = [], n = Math.min.apply(null, e); n <= t; n++) i.push(n);
                    return i
                }(o) : t.map(function (t) {
                    return t[e]
                }), axisLabel: {
                    formatter: function (e) {
                        return String(e)
                    }
                }, show: a
            }
        })
    }

    function B(e) {
        for (var t = e.meaAxisName, i = e.meaAxisType, n = e.axisVisible, a = e.digit, r = e.scale, o = e.min, s = e.max, l = {
            type: "value",
            axisTick: {show: !1},
            show: n
        }, c = [], u = function (e) {
            i[e] ? c[e] = S({}, l, {
                axisLabel: {
                    formatter: function (t) {
                        return z(t, i[e], a)
                    }
                }
            }) : c[e] = S({}, l), c[e].name = t[e] || "", c[e].scale = r[e] || !1, c[e].min = o[e] || null, c[e].max = s[e] || null
        }, d = 0; d < 2; d++) u(d);
        return c
    }

    function W(e) {
        var t = e.axisSite, i = e.isHistogram, n = e.meaAxisType, a = e.digit, o = e.labelMap,
            s = i ? t.right || [] : t.top || [];
        return o && (s = s.map(function (e) {
            return void 0 === o[e] ? e : o[e]
        })), {
            trigger: "axis", formatter: function (e) {
                var t = [];
                return t.push(e[0].name + "<br>"), e.forEach(function (e) {
                    var i = e.seriesName, o = ~s.indexOf(i) ? n[1] : n[0];
                    t.push(r(e.color)), t.push(i + ": "), t.push(z(e.value, o, a)), t.push("<br>")
                }), t.join("")
            }
        }
    }

    function I(e) {
        var t, i = e.innerRows, n = e.metrics, a = e.stack, r = e.axisSite, o = e.isHistogram, s = e.labelMap,
            l = e.itemStyle, c = e.label, u = e.showLine, d = void 0 === u ? [] : u, m = e.dimAxisType, p = e.barGap,
            h = e.opacity, f = e.dims, v = {}, b = o ? r.right || [] : r.top || [], y = o ? "yAxisIndex" : "xAxisIndex",
            g = a && R(a);
        return n.forEach(function (e) {
            v[e] = []
        }), i.forEach(function (e) {
            n.forEach(function (t) {
                v[t].push(e[t])
            })
        }), !!(t = Object.keys(v).map(function (e, t) {
            var i = "value" === m ? function (e, t) {
                for (var i = Math.max.apply(null, t), n = [], a = Math.min.apply(null, t); a <= i; a++) {
                    var r = t.indexOf(a);
                    ~r ? n.push(e[r]) : n.push(null)
                }
                return n
            }(v[e], f) : v[e], n = w({
                name: null != s[e] ? s[e] : e,
                type: ~d.indexOf(e) ? "line" : "bar",
                data: i
            }, y, ~b.indexOf(e) ? "1" : "0");
            a && g[e] && (n.stack = g[e]), c && (n.label = c), l && (n.itemStyle = l);
            var r = h || function (e, t, i) {
                if (!t) return e;
                var n = e;
                return t.split(".").some(function (e, t) {
                    if (void 0 === n[e]) return n = i, !0;
                    n = n[e]
                }), n
            }(n, "itemStyle.normal.opacity");
            return "value" === m && (n.barGap = p, n.barCategoryGap = "1%", null == r && (r = $)), null != r && M(n, "itemStyle.normal.opacity", r), n
        })).length && t
    }

    function Z(e) {
        var t = e.metrics, i = e.labelMap, n = e.legendName;
        return n || i ? {
            data: i ? t.map(function (e) {
                return null == i[e] ? e : i[e]
            }) : t, formatter: function (e) {
                return null != n[e] ? n[e] : e
            }
        } : {data: t}
    }

    function J(e, t) {
        return e.map(function (e) {
            return e[t[0]]
        })
    }

    var U = function (e, t, i, n) {
        var a = j(t), r = i.axisSite, o = void 0 === r ? {} : r, s = i.dimension, l = void 0 === s ? [e[0]] : s,
            c = i.stack, u = void 0 === c ? {} : c, d = i.axisVisible, m = void 0 === d || d, p = i.digit,
            h = void 0 === p ? 2 : p, f = i.dataOrder, v = void 0 !== f && f, b = i.scale,
            y = void 0 === b ? [!1, !1] : b, g = i.min, x = void 0 === g ? [null, null] : g, w = i.max,
            S = void 0 === w ? [null, null] : w, A = i.legendName, O = void 0 === A ? {} : A, M = i.labelMap,
            V = void 0 === M ? {} : M, T = i.label, N = i.itemStyle, k = i.showLine, L = i.barGap,
            E = void 0 === L ? "-100%" : L, z = i.opacity, R = n.tooltipVisible, C = n.legendVisible, H = e.slice();
        o.top && o.bottom ? H = o.top.concat(o.bottom) : o.bottom && !o.right ? H = o.bottom : i.metrics ? H = i.metrics : H.splice(e.indexOf(l[0]), 1);
        var F = i.xAxisType || ["normal", "normal"], _ = i.yAxisType || "category", P = i.xAxisName || [],
            D = i.yAxisName || "";
        if (v) {
            var q = v.label, $ = v.order;
            q && $ ? a.sort(function (e, t) {
                return "desc" === $ ? e[q] - t[q] : t[q] - e[q]
            }) : console.warn("Need to provide name and order parameters")
        }
        var U = J(a, l), Y = C && Z({metrics: H, labelMap: V, legendName: O}),
            X = G({innerRows: a, dimAxisName: D, dimension: l, axisVisible: m, dimAxisType: _, dims: U}),
            K = B({meaAxisName: P, meaAxisType: F, axisVisible: m, digit: h, scale: y, min: x, max: S});
        return {
            legend: Y,
            yAxis: X,
            series: I({
                innerRows: a,
                metrics: H,
                stack: u,
                axisSite: o,
                isHistogram: !1,
                labelMap: V,
                itemStyle: N,
                label: T,
                showLine: k,
                dimAxisType: _,
                dimension: l,
                barGap: E,
                opacity: z,
                dims: U
            }),
            xAxis: K,
            tooltip: R && W({axisSite: o, isHistogram: !1, meaAxisType: F, digit: h, labelMap: V})
        }
    }, Y = function (e, t, i, n) {
        var a = j(t), r = i.axisSite, o = void 0 === r ? {} : r, s = i.dimension, l = void 0 === s ? [e[0]] : s,
            c = i.stack, u = void 0 === c ? {} : c, d = i.axisVisible, m = void 0 === d || d, p = i.digit,
            h = void 0 === p ? 2 : p, f = i.dataOrder, v = void 0 !== f && f, b = i.scale,
            y = void 0 === b ? [!1, !1] : b, g = i.min, x = void 0 === g ? [null, null] : g, w = i.max,
            S = void 0 === w ? [null, null] : w, A = i.labelMap, O = void 0 === A ? {} : A, M = i.legendName,
            V = void 0 === M ? {} : M, T = i.label, N = i.itemStyle, k = i.showLine, L = i.barGap,
            E = void 0 === L ? "-100%" : L, z = i.opacity;
        if (v) {
            var R = v.label, C = v.order;
            R && C ? a.sort(function (e, t) {
                return "desc" === C ? e[R] - t[R] : t[R] - e[R]
            }) : console.warn("Need to provide name and order parameters")
        }
        var H = n.tooltipVisible, F = n.legendVisible, _ = e.slice();
        o.left && o.right ? _ = o.left.concat(o.right) : o.left && !o.right ? _ = o.left : i.metrics ? _ = i.metrics : _.splice(e.indexOf(l[0]), 1);
        var P = i.yAxisType || ["normal", "normal"], D = i.xAxisType || "category", q = i.yAxisName || [],
            $ = i.xAxisName || "", U = J(a, l), Y = F && Z({metrics: _, labelMap: O, legendName: V}),
            X = G({innerRows: a, dimAxisName: $, dimension: l, axisVisible: m, dimAxisType: D, dims: U});
        return {
            legend: Y,
            yAxis: B({meaAxisName: q, meaAxisType: P, axisVisible: m, digit: h, scale: y, min: x, max: S}),
            series: I({
                innerRows: a,
                metrics: _,
                stack: u,
                axisSite: o,
                isHistogram: !0,
                labelMap: O,
                itemStyle: N,
                label: T,
                showLine: k,
                dimAxisType: D,
                dimension: l,
                barGap: E,
                opacity: z,
                dims: U
            }),
            xAxis: X,
            tooltip: H && W({axisSite: o, isHistogram: !0, meaAxisType: P, digit: h, labelMap: O})
        }
    }, X = {
        render: function () {
            var e = this.$createElement, t = this._self._c || e;
            return t("div", {staticClass: "v-charts-component-loading"}, [t("div", {staticClass: "loader"}, [t("div", {staticClass: "loading-spinner"}, [t("svg", {
                staticClass: "circular",
                attrs: {viewBox: "25 25 50 50"}
            }, [t("circle", {staticClass: "path", attrs: {cx: "50", cy: "50", r: "20", fill: "none"}})])])])])
        }, staticRenderFns: []
    }, K = {
        render: function () {
            var e = this.$createElement;
            return (this._self._c || e)("div", {staticClass: "v-charts-data-empty"}, [this._v(" 暂无数据 ")])
        }, staticRenderFns: []
    };

    function Q(e, t) {
        Object.keys(t).forEach(function (i) {
            t[i] && (e[i] = t[i])
        })
    }

    var ee = {
        render: function (e) {
            return e("div", {
                class: [(t = this.$options.name || this.$options._componentTag, t.replace(/([a-z])([A-Z])/g, "$1-$2").toLowerCase())],
                style: this.canvasStyle
            }, [e("div", {
                style: this.canvasStyle,
                class: {"v-charts-mask-status": this.dataEmpty || this.loading},
                ref: "canvas"
            }), e(K, {style: {display: this.dataEmpty ? "" : "none"}}), e(X, {style: {display: this.loading ? "" : "none"}}), this.$slots.default]);
            var t
        }, props: {
            data: {
                type: [Object, Array], default: function () {
                    return {}
                }
            },
            settings: {
                type: Object, default: function () {
                    return {}
                }
            },
            width: {type: String, default: "auto"},
            height: {type: String, default: "400px"},
            beforeConfig: {type: Function},
            afterConfig: {type: Function},
            afterSetOption: {type: Function},
            afterSetOptionOnce: {type: Function},
            events: {type: Object},
            grid: {type: [Object, Array]},
            colors: {type: Array},
            tooltipVisible: {type: Boolean, default: !0},
            legendVisible: {type: Boolean, default: !0},
            legendPosition: {type: String},
            markLine: {type: Object},
            markArea: {type: Object},
            markPoint: {type: Object},
            visualMap: {type: [Object, Array]},
            dataZoom: {type: [Object, Array]},
            toolbox: {type: [Object, Array]},
            initOptions: {
                type: Object, default: function () {
                    return {}
                }
            },
            title: [Object, Array],
            legend: [Object, Array],
            xAxis: [Object, Array],
            yAxis: [Object, Array],
            radar: Object,
            tooltip: Object,
            axisPointer: [Object, Array],
            brush: [Object, Array],
            geo: [Object, Array],
            timeline: [Object, Array],
            graphic: [Object, Array],
            series: [Object, Array],
            backgroundColor: [Object, String],
            textStyle: [Object, Array],
            animation: Object,
            theme: Object,
            themeName: String,
            loading: Boolean,
            dataEmpty: Boolean,
            extend: Object,
            judgeWidth: {type: Boolean, default: !1},
            widthChangeDelay: {type: Number, default: 300},
            tooltipFormatter: {type: Function},
            resizeable: {type: Boolean, default: !0},
            resizeDelay: {type: Number, default: 200},
            changeDelay: {type: Number, default: 0},
            setOptionOpts: {type: [Boolean, Object], default: !0},
            cancelResizeCheck: Boolean,
            notSetUnchange: Array,
            log: Boolean
        }, watch: {
            data: {
                deep: !0, handler: function (e) {
                    e && this.changeHandler()
                }
            },
            settings: {
                deep: !0, handler: function (e) {
                    e.type && this.chartLib && (this.chartHandler = this.chartLib[e.type]), this.changeHandler()
                }
            },
            width: "nextTickResize",
            height: "nextTickResize",
            events: {deep: !0, handler: "createEventProxy"},
            theme: {deep: !0, handler: "themeChange"},
            themeName: "themeChange",
            resizeable: "resizeableHandler"
        }, computed: {
            canvasStyle: function () {
                return {width: this.width, height: this.height, position: "relative"}
            }, chartColor: function () {
                return this.colors || this.theme && this.theme.color || i
            }
        }, methods: {
            dataHandler: function () {
                if (this.chartHandler) {
                    var e = this.data, t = e, i = t.columns, n = void 0 === i ? [] : i, a = t.rows,
                        r = void 0 === a ? [] : a, o = {
                            tooltipVisible: this.tooltipVisible,
                            legendVisible: this.legendVisible,
                            echarts: this.echarts,
                            color: this.chartColor,
                            tooltipFormatter: this.tooltipFormatter,
                            _once: this._once
                        };
                    this.beforeConfig && (e = this.beforeConfig(e));
                    var s = this.chartHandler(n, r, this.settings, o);
                    s && ("function" == typeof s.then ? s.then(this.optionsHandler) : this.optionsHandler(s))
                }
            }, nextTickResize: function () {
                this.$nextTick(this.resize)
            }, resize: function () {
                this.cancelResizeCheck ? this.echartsResize() : this.$el && this.$el.clientWidth && this.$el.clientHeight && this.echartsResize()
            }, echartsResize: function () {
                this.echarts && this.echarts.resize()
            }, optionsHandler: function (t) {
                var i = this;
                if (this.legendPosition && t.legend && (t.legend[this.legendPosition] = 10, ~["left", "right"].indexOf(this.legendPosition) && (t.legend.top = "middle", t.legend.orient = "vertical")), t.color = this.chartColor, s.forEach(function (e) {
                    i[e] && (t[e] = i[e])
                }), this.animation && function (e, t) {
                    Object.keys(t).forEach(function (i) {
                        e[i] = t[i]
                    })
                }(t, this.animation), this.markArea || this.markLine || this.markPoint) {
                    var n = {markArea: this.markArea, markLine: this.markLine, markPoint: this.markPoint}, a = t.series;
                    L(a) ? a.forEach(function (e) {
                        Q(e, n)
                    }) : k(a) && Q(a, n)
                }
                this.extend && function (e, t) {
                    Object.keys(t).forEach(function (i) {
                        var n = t[i];
                        ~i.indexOf(".") ? M(e, i, n) : "function" == typeof n ? e[i] = n(e[i]) : L(e[i]) && k(e[i][0]) ? e[i].forEach(function (t, a) {
                            e[i][a] = S({}, t, n)
                        }) : k(e[i]) ? e[i] = S({}, e[i], n) : e[i] = n
                    })
                }(t, this.extend), this.afterConfig && (t = this.afterConfig(t));
                var r = this.setOptionOpts;
                !this.settings.bmap && !this.settings.amap || k(r) || (r = !1), this.notSetUnchange && this.notSetUnchange.length && (this.notSetUnchange.forEach(function (e) {
                    var n = t[e];
                    n && (!function e(t, i) {
                        if (t === i) return !0;
                        if (null === t || null === i || "object" !== N(t) || "object" !== N(i)) return t === i;
                        for (var n in t) if (E(t, n)) {
                            var a = t[n], r = i[n], o = N(a);
                            if ("undefined" === N(r)) return !1;
                            if ("object" === o) {
                                if (!e(a, r)) return !1
                            } else if (a !== r) return !1
                        }
                        for (var s in i) if (E(i, s) && "undefined" === N(t)[s]) return !1;
                        return !0
                    }(n, i._store[e]) ? i._store[e] = j(n) : t[e] = void 0)
                }), k(r) ? r.notMerge = !1 : r = !1), this._isDestroyed || (this.log && console.log(t), this.echarts.setOption(t, r), this.$emit("ready", this.echarts, t, e), this._once["ready-once"] || (this._once["ready-once"] = !0, this.$emit("ready-once", this.echarts, t, e)), this.judgeWidth && this.judgeWidthHandler(t), this.afterSetOption && this.afterSetOption(this.echarts, t, e), this.afterSetOptionOnce && !this._once.afterSetOptionOnce && (this._once.afterSetOptionOnce = !0, this.afterSetOptionOnce(this.echarts, t, e)))
            }, judgeWidthHandler: function (e) {
                var t = this, i = this.widthChangeDelay, n = this.resize;
                this.$el.clientWidth || this.$el.clientHeight ? n() : this.$nextTick(function (e) {
                    t.$el.clientWidth || t.$el.clientHeight ? n() : setTimeout(function (e) {
                        n(), t.$el.clientWidth && t.$el.clientHeight || console.warn(" Can't get dom width or height ")
                    }, i)
                })
            }, resizeableHandler: function (e) {
                e && !this._once.onresize && this.addResizeListener(), !e && this._once.onresize && this.removeResizeListener()
            }, init: function () {
                if (!this.echarts) {
                    var i = this.themeName || this.theme || t;
                    this.echarts = e.init(this.$refs.canvas, i, this.initOptions), this.data && this.changeHandler(), this.createEventProxy(), this.resizeable && this.addResizeListener()
                }
            }, addResizeListener: function () {
                window.addEventListener("resize", this.resizeHandler), this._once.onresize = !0
            }, removeResizeListener: function () {
                window.removeEventListener("resize", this.resizeHandler), this._once.onresize = !1
            }, addWatchToProps: function () {
                var e = this, t = this._watchers.map(function (e) {
                    return e.expression
                });
                Object.keys(this.$props).forEach(function (i) {
                    if (!~t.indexOf(i) && !~o.indexOf(i)) {
                        var n = {};
                        ~["[object Object]", "[object Array]"].indexOf(T(e.$props[i])) && (n.deep = !0), e.$watch(i, function () {
                            e.changeHandler()
                        }, n)
                    }
                })
            }, createEventProxy: function () {
                var e = this, t = this, i = Object.keys(this.events || {});
                i.length && i.forEach(function (i) {
                    -1 === e.registeredEvents.indexOf(i) && (e.registeredEvents.push(i), e.echarts.on(i, function (e) {
                        return function () {
                            if (e in t.events) {
                                for (var i = arguments.length, n = Array(i), a = 0; a < i; a++) n[a] = arguments[a];
                                t.events[e].apply(null, n)
                            }
                        }
                    }(i)))
                })
            }, themeChange: function (e) {
                this.clean(), this.echarts = null, this.init()
            }, clean: function () {
                this.resizeable && this.removeResizeListener(), this.echarts.dispose()
            }
        }, created: function () {
            this.echarts = null, this.registeredEvents = [], this._once = {}, this._store = {}, this.resizeHandler = O(this.resize, this.resizeDelay), this.changeHandler = O(this.dataHandler, this.changeDelay), this.addWatchToProps()
        }, mounted: function () {
            this.init()
        }, beforeDestroy: function () {
            this.clean()
        }, _numerify: g
    }, te = S({}, ee, {
        name: "VeBar", data: function () {
            return this.chartHandler = U, {}
        }
    }), ie = S({}, ee, {
        name: "VeHistogram", data: function () {
            return this.chartHandler = Y, {}
        }
    });
    var ne = function (e, t, i, n) {
        t = L(t) ? t : [], e = L(e) ? e : [];
        var a = i.axisSite, r = void 0 === a ? {} : a, o = i.yAxisType, s = void 0 === o ? ["normal", "normal"] : o,
            l = i.xAxisType, c = void 0 === l ? "category" : l, u = i.yAxisName, d = void 0 === u ? [] : u,
            m = i.dimension, p = void 0 === m ? [e[0]] : m, h = i.xAxisName, f = void 0 === h ? [] : h,
            v = i.axisVisible, b = void 0 === v || v, y = i.area, g = i.stack, x = i.scale,
            w = void 0 === x ? [!1, !1] : x, A = i.min, O = void 0 === A ? [null, null] : A, M = i.max,
            V = void 0 === M ? [null, null] : M, T = i.nullAddZero, N = void 0 !== T && T, k = i.digit,
            j = void 0 === k ? 2 : k, E = i.legendName, C = void 0 === E ? {} : E, H = i.labelMap,
            F = void 0 === H ? {} : H, _ = i.label, P = i.itemStyle, D = i.lineStyle, q = i.areaStyle,
            $ = n.tooltipVisible, G = n.legendVisible, B = n.tooltipFormatter, W = e.slice();
        r.left && r.right ? W = r.left.concat(r.right) : r.left && !r.right ? W = r.left : i.metrics ? W = i.metrics : W.splice(e.indexOf(p[0]), 1);
        var I = G && function (e) {
            var t = e.metrics, i = e.legendName, n = e.labelMap;
            return i || n ? {
                data: n ? t.map(function (e) {
                    return null == n[e] ? e : n[e]
                }) : t, formatter: function (e) {
                    return null != i[e] ? i[e] : e
                }
            } : {data: t}
        }({metrics: W, legendName: C, labelMap: F}), Z = $ && function (e) {
            var t = e.axisSite, i = e.yAxisType, n = e.digit, a = e.labelMap, r = e.tooltipFormatter, o = t.right || [],
                s = a ? o.map(function (e) {
                    return void 0 === a[e] ? e : a[e]
                }) : o;
            return {
                trigger: "axis", formatter: function (e) {
                    if (r) return r.apply(null, arguments);
                    var t = [], a = e[0], o = a.name, l = a.axisValueLabel, c = o || l;
                    return t.push(c + "<br>"), e.forEach(function (e) {
                        var a, r = e.seriesName, o = e.data, l = e.marker, c = ~s.indexOf(r) ? i[1] : i[0],
                            u = L(o) ? o[1] : o;
                        a = z(u, c, n), t.push(l), t.push(r + ": " + a), t.push("<br>")
                    }), t.join("")
                }
            }
        }({axisSite: r, yAxisType: s, digit: j, labelMap: F, xAxisType: c, tooltipFormatter: B}), J = function (e) {
            var t = e.dimension, i = e.rows, n = e.xAxisName, a = e.axisVisible, r = e.xAxisType;
            return t.map(function (e, t) {
                return {
                    type: r,
                    nameLocation: "middle",
                    nameGap: 22,
                    name: n[t] || "",
                    axisTick: {show: !0, lineStyle: {color: "#eee"}},
                    data: i.map(function (t) {
                        return t[e]
                    }),
                    show: a
                }
            })
        }({dimension: p, rows: t, xAxisName: f, axisVisible: b, xAxisType: c}), U = function (e) {
            for (var t = e.yAxisName, i = e.yAxisType, n = e.axisVisible, a = e.scale, r = e.min, o = e.max, s = e.digit, l = {
                type: "value",
                axisTick: {show: !1},
                show: n
            }, c = [], u = function (e) {
                i[e] ? c[e] = S({}, l, {
                    axisLabel: {
                        formatter: function (t) {
                            return z(t, i[e], s)
                        }
                    }
                }) : c[e] = S({}, l), c[e].name = t[e] || "", c[e].scale = a[e] || !1, c[e].min = r[e] || null, c[e].max = o[e] || null
            }, d = 0; d < 2; d++) u(d);
            return c
        }({yAxisName: d, yAxisType: s, axisVisible: b, scale: w, min: O, max: V, digit: j});
        return {
            legend: I, xAxis: J, series: function (e) {
                var t = e.rows, i = e.axisSite, n = e.metrics, a = e.area, r = e.stack, o = e.nullAddZero,
                    s = e.labelMap, l = e.label, c = e.itemStyle, u = e.lineStyle, d = e.areaStyle, m = e.dimension,
                    p = [], h = {}, f = r && R(r);
                return n.forEach(function (e) {
                    h[e] = []
                }), t.forEach(function (e) {
                    n.forEach(function (t) {
                        var i = null;
                        null != e[t] ? i = e[t] : o && (i = 0), h[t].push([e[m[0]], i])
                    })
                }), n.forEach(function (e) {
                    var t = {name: null != s[e] ? s[e] : e, type: "line", data: h[e]};
                    a && (t.areaStyle = {normal: {}}), i.right && (t.yAxisIndex = ~i.right.indexOf(e) ? 1 : 0), r && f[e] && (t.stack = f[e]), l && (t.label = l), c && (t.itemStyle = c), u && (t.lineStyle = u), d && (t.areaStyle = d), p.push(t)
                }), p
            }({
                rows: t,
                axisSite: r,
                metrics: W,
                area: y,
                stack: g,
                nullAddZero: N,
                labelMap: F,
                label: _,
                itemStyle: P,
                lineStyle: D,
                areaStyle: q,
                xAxisType: c,
                dimension: p
            }), yAxis: U, tooltip: Z
        }
    }, ae = S({}, ee, {
        name: "VeLine", data: function () {
            return this.chartHandler = ne, {}
        }
    }), re = [80, 100], oe = [20, 100];
    var se = function (e, t, i, n, a) {
        var o = j(t), s = i.dataType, l = void 0 === s ? "normal" : s, c = i.percentShow, u = i.dimension,
            d = void 0 === u ? e[0] : u, m = i.metrics, p = void 0 === m ? e[1] : m, h = i.roseType,
            f = void 0 !== h && h, v = i.radius, b = void 0 === v ? a ? f ? oe : re : 100 : v, y = i.offsetY,
            g = void 0 === y ? 200 : y, x = i.legendLimit, w = void 0 === x ? 30 : x, A = i.selectedMode,
            O = void 0 !== A && A, M = i.hoverAnimation, V = void 0 === M || M, T = i.digit, N = void 0 === T ? 2 : T,
            k = i.legendName, L = void 0 === k ? {} : k, E = i.label, R = void 0 !== E && E, C = i.level,
            H = void 0 !== C && C, F = i.limitShowNum, _ = void 0 === F ? 0 : F, P = i.labelLine, D = i.itemStyle,
            $ = n.tooltipVisible, G = n.legendVisible;
        return _ && o.sort(function (e, t) {
            return t[p] - e[p]
        }), {
            series: function (e) {
                var t = e.innerRows, i = e.dataType, n = e.percentShow, a = e.dimension, r = e.metrics, o = e.radius,
                    s = e.offsetY, l = e.selectedMode, c = e.hoverAnimation, u = e.digit, d = e.roseType, m = e.label,
                    p = e.level, h = e.limitShowNum, f = e.isRing, v = e.labelLine, b = e.itemStyle, y = [], g = {},
                    x = [];
                p ? (p.forEach(function (e, t) {
                    e.forEach(function (e) {
                        q(g, e, t)
                    })
                }), t.forEach(function (e) {
                    var t = g[e[a]];
                    t && t.length && t.forEach(function (t) {
                        q(x, t, e)
                    })
                })) : x.push(t);
                var w = {type: "pie", selectedMode: l, hoverAnimation: c, roseType: d, center: ["50%", s]},
                    A = x.length;
                if (x.forEach(function (e, t) {
                    var s = S({data: []}, w), l = o / A;
                    if (t) {
                        var c = l + o / (2 * A) * (2 * t - 1), d = c + o / (2 * A);
                        s.radius = [c, d]
                    } else s.radius = f ? o : l;
                    A > 1 && 0 === t && (s.label = {normal: {position: "inner"}}), m && (s.label = m), v && (s.labelLine = v), b && (s.itemStyle = b), n && (s.label = {
                        normal: {
                            show: !0,
                            position: A > 1 && 0 === t ? "inner" : "outside",
                            formatter: function (e) {
                                var t = [];
                                return t.push(e.name + ":"), t.push(z(e.value, i, u)), t.push("(" + e.percent + "%)"), t.join(" ")
                            }
                        }
                    }), s.data = e.map(function (e) {
                        return {name: e[a], value: e[r]}
                    }), y.push(s)
                }), h && h < y[0].data.length) {
                    var O = y[0].data, M = 0;
                    O.slice(h, O.length).forEach(function (e) {
                        M += e.value
                    }), y[0].data = O.slice(0, h), y[0].data.push({name: "其他", value: M})
                }
                return y
            }({
                innerRows: o,
                dataType: l,
                percentShow: c,
                dimension: d,
                metrics: p,
                radius: b,
                offsetY: g,
                selectedMode: O,
                hoverAnimation: V,
                digit: N,
                roseType: f,
                label: R,
                level: H,
                legendName: L,
                limitShowNum: _,
                isRing: a,
                labelLine: P,
                itemStyle: D
            }),
            legend: G && function (e) {
                var t = e.innerRows, i = e.dimension, n = e.legendLimit, a = e.legendName, r = e.level,
                    o = e.limitShowNum, s = [], l = [];
                if (r) r.forEach(function (e) {
                    e.forEach(function (e) {
                        l.push(e)
                    })
                }), s = l; else if (o && o < t.length) {
                    for (var c = 0; c < o; c++) s.push(t[c][i]);
                    s.push("其他")
                } else s = t.map(function (e) {
                    return e[i]
                });
                return !!s.length && {
                    data: s, show: s.length < n, formatter: function (e) {
                        return null != a[e] ? a[e] : e
                    }
                }
            }({innerRows: o, dimension: d, legendLimit: w, legendName: L, level: H, limitShowNum: _}),
            tooltip: $ && function (e) {
                var t = e.dataType, i = e.innerRows, n = e.limitShowNum, a = e.digit, o = e.metrics, s = e.dimension,
                    l = 0, c = i.map(function (e) {
                        return l += e[o], {name: e[s], value: e[o]}
                    }).slice(n, i.length);
                return {
                    formatter: function (e) {
                        var i = [];
                        return i.push(r(e.color)), n && "其他" === e.name ? (i.push("其他:"), c.forEach(function (e) {
                            var n = e.name, r = e.value, o = z(r / l, "percent");
                            i.push("<br>" + n + ":"), i.push(z(r, t, a)), i.push("(" + o + ")")
                        })) : (i.push(e.name + ":"), i.push(z(e.value, t, a)), i.push("(" + e.percent + "%)")), i.join(" ")
                    }
                }
            }({dataType: l, innerRows: o, limitShowNum: _, digit: N, metrics: p, dimension: d})
        }
    }, le = function (e, t, i, n) {
        return se(e, t, i, n, !0)
    }, ce = S({}, ee, {
        name: "VePie", data: function () {
            return this.chartHandler = se, {}
        }
    }), ue = S({}, ee, {
        name: "VeRing", data: function () {
            return this.chartHandler = le, {}
        }
    });
    var de = function (e, t, i, n) {
        var a = i.dataType, r = void 0 === a ? "normal" : a, o = i.dimension, s = void 0 === o ? e[0] : o,
            l = i.totalName, c = void 0 === l ? "总计" : l, u = i.totalNum, d = i.remainName, m = void 0 === d ? "其他" : d,
            p = i.xAxisName, h = void 0 === p ? s : p, f = i.labelMap, v = void 0 === f ? {} : f, b = i.axisVisible,
            y = void 0 === b || b, g = i.digit, x = void 0 === g ? 2 : g, w = n.tooltipVisible, A = e.slice();
        A.splice(A.indexOf(s), 1);
        var O = A[0], M = O, V = w && function (e, t) {
            return {
                trigger: "axis", axisPointer: {type: "shadow"}, formatter: function (i) {
                    var n = i[1];
                    return [n.name + "<br/>" + n.seriesName + " :", "" + z(n.value, e, t)].join("")
                }
            }
        }(r, x), T = parseFloat(t.reduce(function (e, t) {
            return e + Number(t[O])
        }, 0).toFixed(x)), N = function (e, t) {
            return t ? t > e ? "have-remain" : "none-remain" : "not-total"
        }(T, u);
        return {
            tooltip: V, xAxis: function (e) {
                var t = e.dimension, i = e.rows, n = e.remainStatus, a = e.totalName, r = e.remainName, o = e.labelMap,
                    s = e.xAxisName, l = e.axisVisible, c = [a].concat(i.map(function (e) {
                        return e[t]
                    }));
                return "have-remain" === n && (c = c.concat([r])), {
                    type: "category",
                    name: o && o[s] || s,
                    splitLine: {show: !1},
                    data: c,
                    show: l
                }
            }({
                dimension: s,
                rows: t,
                remainStatus: N,
                totalName: c,
                remainName: m,
                xAxisName: h,
                labelMap: v,
                axisVisible: y
            }), yAxis: function (e) {
                var t = e.dataType, i = e.yAxisName, n = e.axisVisible, a = e.digit, r = e.labelMap;
                return {
                    type: "value",
                    name: null != r[i] ? r[i] : i,
                    axisTick: {show: !1},
                    axisLabel: {
                        formatter: function (e) {
                            return z(e, t, a)
                        }
                    },
                    show: n
                }
            }({dataType: r, yAxisName: M, axisVisible: y, digit: x, labelMap: v}), series: function (e) {
                var t = e.dataType, i = e.rows, n = e.metrics, a = e.totalNum, r = e.remainStatus, o = e.dataSum,
                    s = e.digit, l = {type: "bar", stack: "总量"}, c = o, u = a, d = void 0, m = void 0,
                    p = i.map(function (e) {
                        return e[n]
                    });
                "have-remain" === r ? (d = [0].concat(i.map(function (e) {
                    return u -= e[n]
                })).concat([0]), m = [a].concat(p).concat([a - o])) : (d = [0].concat(i.map(function (e) {
                    return c -= e[n]
                })), m = [o].concat(p));
                var h = [];
                return h.push(S({
                    name: "辅助",
                    itemStyle: {normal: {opacity: 0}, emphasis: {opacity: 0}},
                    data: d
                }, l)), h.push(S({
                    name: "数值", label: {
                        normal: {
                            show: !0, position: "top", formatter: function (e) {
                                return z(e.value, t, s)
                            }
                        }
                    }, data: m
                }, l)), h
            }({dataType: r, rows: t, dimension: s, metrics: O, totalNum: u, remainStatus: N, dataSum: T, digit: x})
        }
    }, me = S({}, ee, {
        name: "VeWaterfall", data: function () {
            return this.chartHandler = de, {}
        }
    });
    var pe = function (e, t, i, n) {
        var a = e.slice(), o = t.slice(), s = i.dataType, l = void 0 === s ? "normal" : s, c = i.dimension,
            u = void 0 === c ? a[0] : c, d = i.sequence, m = void 0 === d ? o.map(function (e) {
                return e[u]
            }) : d, p = i.digit, h = void 0 === p ? 2 : p, f = i.ascending, v = i.label, b = i.labelLine, y = i.legendName,
            g = void 0 === y ? {} : y, x = i.itemStyle, w = i.filterZero, S = i.useDefaultOrder, A = n.tooltipVisible,
            O = n.legendVisible, M = void 0;
        if (i.metrics) M = i.metrics; else {
            var V = a.slice();
            V.splice(a.indexOf(u), 1), M = V[0]
        }
        return {
            tooltip: A && function (e, t) {
                return {
                    trigger: "item", formatter: function (i) {
                        var n = [];
                        return n.push(r(i.color)), n.push(i.name + ": " + z(i.data.realValue, e, t)), n.join("")
                    }
                }
            }(l, h), legend: O && function (e) {
                var t = e.data, i = e.legendName;
                return {
                    data: t, formatter: function (e) {
                        return null != i[e] ? i[e] : e
                    }
                }
            }({data: m, legendName: g}), series: function (e) {
                var t = e.dimension, i = e.metrics, n = e.rows, a = e.sequence, r = e.ascending, o = e.label,
                    s = e.labelLine, l = e.itemStyle, c = e.filterZero, u = e.useDefaultOrder, d = {type: "funnel"},
                    m = n.sort(function (e, i) {
                        return a.indexOf(e[t]) - a.indexOf(i[t])
                    });
                c && (m = m.filter(function (e) {
                    return e[i]
                }));
                var p = !1;
                m.some(function (e, t) {
                    if (t && e[i] > m[t - 1][i]) return p = !0, !0
                });
                var h = 100 / m.length;
                return d.data = p && !u ? m.slice().reverse().map(function (e, n) {
                    return {name: e[t], value: (n + 1) * h, realValue: e[i]}
                }) : m.map(function (e) {
                    return {name: e[t], value: e[i], realValue: e[i]}
                }), r && (d.sort = "ascending"), o && (d.label = o), s && (d.labelLine = s), l && (d.itemStyle = l), d
            }({
                dimension: u,
                metrics: M,
                rows: o,
                sequence: m,
                ascending: f,
                label: v,
                labelLine: b,
                itemStyle: x,
                filterZero: w,
                useDefaultOrder: S
            })
        }
    }, he = S({}, ee, {
        name: "VeFunnel", data: function () {
            return this.chartHandler = pe, {}
        }
    });
    var fe = function (e, t, i, n) {
        var a = i.dataType, o = void 0 === a ? {} : a, s = i.legendName, l = void 0 === s ? {} : s, c = i.labelMap,
            u = void 0 === c ? {} : c, d = i.dimension, m = void 0 === d ? e[0] : d, p = i.digit,
            h = void 0 === p ? 2 : p, f = i.label, v = i.itemStyle, b = i.lineStyle, y = i.areaStyle,
            g = n.tooltipVisible, x = n.legendVisible, w = e.slice();
        i.metrics ? w = i.metrics : w.splice(e.indexOf(m), 1);
        var S = x && function (e, t, i) {
            return {
                data: e.map(function (e) {
                    return e[t]
                }), formatter: function (e) {
                    return null != i[e] ? i[e] : e
                }
            }
        }(t, m, l), A = function (e, t, i) {
            var n = {indicator: [], shape: "circle", splitNumber: 5}, a = {};
            return e.forEach(function (e) {
                t.forEach(function (t) {
                    var n = null != i[t] ? i[t] : t;
                    a[n] ? a[n].push(e[t]) : a[n] = [e[t]]
                })
            }), n.indicator = Object.keys(a).map(function (e) {
                return {name: e, max: Math.max.apply(null, a[e])}
            }), n
        }(t, w, u);
        return {
            legend: S, tooltip: g && function (e, t, i) {
                var n = [], a = [];
                return t.indicator.map(function (t, i) {
                    n[i] = e[t.name], a[i] = t.name
                }), {
                    formatter: function (e) {
                        var t = [];
                        return t.push(r(e.color)), t.push(e.name + "<br />"), e.data.value.forEach(function (e, r) {
                            t.push(a[r] + ": "), t.push(z(e, n[r], i) + "<br />")
                        }), t.join("")
                    }
                }
            }(o, A, h), radar: A, series: function (e) {
                var t = e.rows, i = e.dimension, n = e.metrics, a = e.radar, r = e.label, o = e.itemStyle,
                    s = e.lineStyle, l = e.labelMap, c = e.areaStyle, u = {};
                a.indicator.forEach(function (e, t) {
                    var i = e.name;
                    u[i] = t
                });
                var d = t.map(function (e) {
                    var t = {value: [], name: e[i]};
                    return Object.keys(e).forEach(function (i) {
                        if (~n.indexOf(i)) {
                            var a = null != l[i] ? u[l[i]] : u[i];
                            t.value[a] = e[i]
                        }
                    }), t
                }), m = {name: i, type: "radar", data: d};
                return r && (m.label = r), o && (m.itemStyle = o), s && (m.lineStyle = s), c && (m.areaStyle = c), [m]
            }({
                rows: t,
                dimension: m,
                metrics: w,
                radar: A,
                label: f,
                itemStyle: v,
                lineStyle: b,
                labelMap: u,
                areaStyle: y
            })
        }
    }, ve = S({}, ee, {
        name: "VeRadar", data: function () {
            return this.chartHandler = fe, {}
        }
    }), be = S({}, ee, {
        name: "VeChart", data: function () {
            return this.chartLib = {
                bar: U,
                histogram: Y,
                line: ne,
                pie: se,
                ring: le,
                funnel: pe,
                radar: fe,
                waterfall: de
            }, this.chartHandler = this.chartLib[this.settings.type], {}
        }
    });

    function ye(e, t, i) {
        "object" === (void 0 === e ? "undefined" : x(e)) ? t[i] = e : e && (t[i] = {
            normal: {show: !0},
            emphasis: {show: !0}
        })
    }

    function ge(t, i) {
        var n = t._once, a = t.registerSign, r = t.beforeRegisterMap, o = t.beforeRegisterMapOnce,
            s = t.registerSignOnce, l = t.position, c = t.specialAreas;
        n[a] || (r && (i = r(i)), o && !n[s] && (n[s] = !0, i = o(i)), n[a] = !0, e.registerMap(l, i, c))
    }

    var xe = function (e, t, i, n) {
        var a = i.position, o = void 0 === a ? "china" : a, s = i.selectData, l = void 0 !== s && s, c = i.selectedMode,
            u = i.label, d = void 0 === u || u, m = i.dataType, p = void 0 === m ? {} : m, h = i.digit,
            f = void 0 === h ? 2 : h, v = i.dimension, b = void 0 === v ? e[0] : v, y = i.roam, g = i.center,
            x = i.aspectScale, w = i.boundingCoords, A = i.zoom, O = i.scaleLimit, M = i.legendName,
            V = void 0 === M ? {} : M, T = i.labelMap, N = void 0 === T ? {} : T, k = i.mapGrid, L = i.itemStyle,
            j = i.positionJsonLink, E = i.beforeRegisterMap, R = i.beforeRegisterMapOnce, C = i.mapURLProfix,
            F = void 0 === C ? "https://unpkg.com/echarts@3.6.2/map/json/" : C, _ = i.specialAreas,
            P = void 0 === _ ? {} : _, D = i.mapOrigin, q = e.slice();
        i.metrics ? q = i.metrics : q.splice(e.indexOf(b), 1);
        var $ = n.tooltipVisible, G = n.legendVisible, B = n.color, W = n._once, I = {};
        t.forEach(function (e) {
            I[e[b]] = e
        });
        var Z = $ && function (e, t, i, n, a, o) {
            return {
                formatter: function (s) {
                    var l = [];
                    return s.name ? (l.push(s.name + "<br>"), n.forEach(function (n, c) {
                        var u = null != o[n] ? o[n] : n;
                        l.push(r(a[c]) + " " + u + " : "), i[s.name] ? l.push(z(i[s.name][n], e[n], t)) : l.push("-"), l.push("<br>")
                    }), l.join(" ")) : ""
                }
            }
        }(p, f, I, q, B, N), J = G && function (e) {
            var t = e.metrics, i = e.legendName, n = e.labelMap;
            return i || n ? {
                data: n ? t.map(function (e) {
                    return null == n[e] ? e : n[e]
                }) : t, formatter: function (e) {
                    return null != i[e] ? i[e] : e
                }
            } : {data: t}
        }({metrics: q, legendName: V, labelMap: N}), U = function (e) {
            var t = e.position, i = e.selectData, n = e.dimension, a = e.metrics, r = e.rows, o = e.label,
                s = e.itemStyle, l = e.selectedMode, c = e.roam, u = e.center, d = e.aspectScale, m = e.boundingCoords,
                p = e.zoom, h = e.labelMap, f = e.scaleLimit, v = e.mapGrid, b = [], y = {type: "map", mapType: t};
            return a.forEach(function (e) {
                var t = S({
                    name: null != h[e] ? h[e] : e,
                    data: [],
                    selectedMode: l,
                    roam: c,
                    center: u,
                    aspectScale: d,
                    boundingCoords: m,
                    zoom: p,
                    scaleLimit: f
                }, y);
                v && Object.keys(v).forEach(function (e) {
                    t[e] = v[e]
                }), ye(s, t, "itemStyle"), ye(o, t, "label"), r.forEach(function (a) {
                    t.data.push({name: a[n], value: a[e], selected: i})
                }), b.push(t)
            }), b
        }({
            position: o,
            selectData: l,
            label: d,
            itemStyle: L,
            dimension: b,
            metrics: q,
            rows: t,
            selectedMode: c,
            roam: y,
            center: g,
            aspectScale: x,
            boundingCoords: w,
            zoom: A,
            labelMap: N,
            scaleLimit: O,
            mapGrid: k
        }), Y = {
            _once: W,
            beforeRegisterMap: E,
            beforeRegisterMapOnce: R,
            registerSign: "MAP_REGISTER_" + o,
            registerSignOnce: "ONCE_MAP_REGISTER_" + o,
            position: o,
            specialAreas: P
        };
        return D ? (ge(Y, D), {series: U, tooltip: Z, legend: J}) : H({
            position: o,
            positionJsonLink: j,
            beforeRegisterMapOnce: R,
            mapURLProfix: F
        }).then(function (e) {
            return ge(Y, e), {series: U, tooltip: Z, legend: J}
        })
    }, we = S({}, ee, {
        name: "VeMap", data: function () {
            return this.chartHandler = xe, {}
        }
    }), Se = function (e, t, i, n) {
        var a = i.key, r = i.v, o = i.bmap, s = i.useOuterMap, l = n._once;
        return a || s || console.warn("settings.key must be a string."), l.bmap_register ? {} : (l.bmap_register = !0, s ? {bmap: o} : P(a, r).then(function (e) {
            return {bmap: o}
        }))
    }, Ae = S({}, ee, {
        name: "VeBmap", data: function () {
            return this.chartHandler = Se, {}
        }
    }), Oe = function (e, t, i, n) {
        var a = i.key, r = i.v, o = i.amap, s = i.useOuterMap, l = n._once;
        return a || s || console.warn("settings.key must be a string."), l.amap_register ? {} : (l.amap_register = !0, s ? {amap: o} : D(a, r).then(function (e) {
            return {amap: o}
        }))
    }, Me = S({}, ee, {
        name: "VeAmap", data: function () {
            return this.chartHandler = Oe, {}
        }
    });
    var Ve = function (e, t, i, n) {
        var a = i.links, o = i.dimension, s = void 0 === o ? e[0] : o, l = i.metrics, c = void 0 === l ? e[1] : l,
            u = i.dataType, d = void 0 === u ? ["normal", "normal"] : u, m = i.digit, p = void 0 === m ? 2 : m,
            h = i.valueFull, f = void 0 !== h && h, v = i.useDataValue, b = void 0 !== v && v, y = i.label,
            g = i.itemStyle, x = i.lineStyle;
        if (a) return {
            tooltip: function (e) {
                var t = e.itemDataType, i = e.linksDataType, n = e.digit;
                return {
                    trigger: "item", formatter: function (e) {
                        var a = [], o = e.name, s = e.data, l = e.value, c = e.color;
                        return a.push(r(c)), a.push(o + " : "), s && s.source ? a.push(z(l, i, n) + "<br />") : a.push(z(l, t, n) + "<br />"), a.join("")
                    }
                }
            }({itemDataType: d[0], linksDataType: d[1], digit: p}), series: function (e) {
                var t = e.rows, i = e.dimension, n = e.metrics, a = e.links, r = e.valueFull, o = e.useDataValue,
                    s = e.label, l = e.itemStyle, c = e.lineStyle, u = {}, d = {
                        type: "sankey", data: t.map(function (e) {
                            return u[e[i]] = e[n], {name: e[i], value: e[n]}
                        }), links: o ? a.map(function (e) {
                            return S({}, e, {value: u[e.target]})
                        }) : r ? a : a.map(function (e) {
                            return null == e.value ? S({}, e, {value: u[e.target]}) : e
                        })
                    };
                return s && (d.label = s), l && (d.itemStyle = l), c && (d.lineStyle = c), [d]
            }({
                rows: t,
                dimension: s,
                metrics: c,
                links: a,
                valueFull: f,
                useDataValue: b,
                label: y,
                itemStyle: g,
                lineStyle: x
            })
        };
        console.warn("links is needed in settings!")
    }, Te = S({}, ee, {
        name: "VeSankey", data: function () {
            return this.chartHandler = Ve, {}
        }
    });

    function Ne(e, t) {
        var i = [];
        return e.forEach(function (e) {
            ~i.indexOf(e[t]) || i.push(e[t])
        }), i
    }

    function ke(e) {
        var t = e.rows, i = e.innerXAxisList, n = e.innerYAxisList, a = e.xDim, r = e.yDim, o = e.metrics, s = e.type,
            l = e.extraMetrics;
        return "cartesian" === s ? t.map(function (e) {
            var t = i.indexOf(e[a]), s = n.indexOf(e[r]), c = o ? e[o] : 1, u = l.map(function (t) {
                return e[t] || "-"
            });
            return {value: [t, s, c].concat(u)}
        }) : t.map(function (e) {
            var t = o ? e[o] : 1;
            return {value: [e[a], e[r], t]}
        })
    }

    function Le(e, t) {
        return {type: "category", data: e, name: t, nameLocation: "end", splitArea: {show: !0}}
    }

    var je = function (t, i, o, s) {
        var l = o.type, c = void 0 === l ? "cartesian" : l, u = o.xAxisList, d = o.yAxisList, m = o.dimension,
            p = void 0 === m ? [t[0], t[1]] : m, h = o.metrics, f = void 0 === h ? t[2] : h, v = o.dataType,
            b = void 0 === v ? "normal" : v, y = o.min, g = o.max, x = o.digit, w = o.bmap, O = o.amap, M = o.geo,
            V = o.key, T = o.v, N = void 0 === T ? "2.0" : T, k = o.position, L = o.positionJsonLink,
            j = o.beforeRegisterMap, E = o.pointSize, R = void 0 === E ? 10 : E, C = o.blurSize,
            F = void 0 === C ? 5 : C, _ = o.heatColor, q = o.yAxisName, $ = o.xAxisName, G = o.beforeRegisterMapOnce,
            B = o.mapURLProfix, W = void 0 === B ? "https://unpkg.com/echarts@3.6.2/map/json/" : B, I = o.specialAreas,
            Z = void 0 === I ? {} : I, J = s.tooltipVisible, U = u, Y = d, X = [], K = [], Q = p.concat([f]);
        t.forEach(function (e) {
            ~Q.indexOf(e) || K.push(e)
        }), "cartesian" === c ? (U && U.length || (U = Ne(i, p[0])), Y && Y.length || (Y = Ne(i, p[1])), X = ke({
            rows: i,
            innerXAxisList: U,
            innerYAxisList: Y,
            xDim: p[0],
            yDim: p[1],
            metrics: f,
            type: c,
            extraMetrics: K
        })) : X = ke({rows: i, xDim: p[0], yDim: p[1], metrics: f, type: c, extraMetrics: K});
        var ee = f ? i.map(function (e) {
            return e[f]
        }) : [0, 5];
        ee.length || (ee = [0]);
        var te = y || Math.min.apply(null, ee), ie = g || Math.max.apply(null, ee), ne = Le(U, $), ae = Le(Y, q),
            re = [{type: "heatmap", data: {chartData: X}.chartData}], oe = function (e) {
                var t = e.innerMin, i = e.innerMax, r = e.type, o = e.heatColor, s = e.series,
                    l = {min: t, max: i, calculable: !0}, c = null;
                return "map" === r ? (c = {
                    orient: "vertical",
                    left: 0,
                    bottom: 0,
                    inRange: {color: o || n}
                }, s[0].data.length || (c.show = !1)) : c = "bmap" === r || "amap" === r ? {
                    show: !1,
                    orient: "vertical",
                    left: 0,
                    bottom: 0,
                    inRange: {color: o || a}
                } : {orient: "horizontal", left: "center", bottom: 10, dimension: 2, inRange: o && {color: o}}, S(l, c)
            }({innerMin: te, innerMax: ie, type: c, heatColor: _, series: re}), se = J && function (e) {
                var t = e.dataType, i = e.innerXAxisList, n = e.innerYAxisList, a = e.digit, o = e.extraMetrics,
                    s = e.metrics;
                return {
                    trigger: "item", formatter: function (e) {
                        var l = e.color, c = A(e.data.value), u = c[0], d = c[1], m = c[2], p = c.slice(3), h = [];
                        return h.push(i[u] + " ~ " + n[d] + "<br>"), o.forEach(function (e, t) {
                            h.push(e + ": " + p[t] + "<br>")
                        }), h.push(r(l) + " " + s + ": " + z(m, t, a) + "<br>"), h.join("")
                    }
                }
            }({dataType: b, innerXAxisList: U, innerYAxisList: Y, digit: x, extraMetrics: K, metrics: f}),
            le = {visualMap: oe, series: re};
        return "bmap" === c ? (S(le.series[0], {
            coordinateSystem: "bmap",
            pointSize: R,
            blurSize: F
        }), P(V, N).then(function (e) {
            return S({bmap: w}, le)
        })) : "map" === c ? (le.series[0].coordinateSystem = "geo", H({
            position: k,
            positionJsonLink: L,
            beforeRegisterMapOnce: G,
            mapURLProfix: W
        }).then(function (t) {
            var i = S({map: k}, M);
            return j && (t = j(t)), e.registerMap(k, t, Z), S({geo: i}, le)
        })) : "amap" === c ? (S(le.series[0], {
            coordinateSystem: "amap",
            pointSize: R,
            blurSize: F
        }), D(V, N).then(function (e) {
            return S({amap: O}, le)
        })) : S({xAxis: ne, yAxis: ae, tooltip: se}, le)
    }, Ee = S({}, ee, {
        name: "VeHeatmap", data: function () {
            return this.chartHandler = je, {}
        }
    });

    function ze(e, t) {
        var i = t.labelMap, n = t.columns, a = t.dataType, o = t.digit, s = [], l = e.color, c = e.seriesName,
            u = e.data.value;
        return s.push(r(l) + " " + c + "<br>"), u.forEach(function (e, t) {
            var r = i[n[t]] || n[t], l = isNaN(e) ? e : z(e, a[n[t]], o);
            s.push(r + ": " + l + "<br>")
        }), s.join("")
    }

    var Re = function (e, t, i, n) {
        var a = i.dimension, r = void 0 === a ? e[0] : a, o = i.metrics, s = void 0 === o ? [e[1], e[2]] : o,
            l = i.dataType, c = void 0 === l ? {} : l, u = i.xAxisType, d = void 0 === u ? "category" : u,
            m = i.xAxisName, p = i.yAxisName, h = i.digit, f = void 0 === h ? 2 : h, v = i.legendName,
            b = void 0 === v ? {} : v, y = i.labelMap, g = void 0 === y ? {} : y, x = i.tooltipTrigger,
            w = void 0 === x ? "item" : x, A = i.axisVisible, O = void 0 === A || A, M = i.symbolSizeMax,
            V = void 0 === M ? 50 : M, T = i.symbol, N = i.symbolSize, k = i.symbolRotate, j = i.symbolOffset,
            E = i.cursor, R = i.min, C = i.max, H = i.scale, F = i.label, _ = i.itemStyle;
        if (L(t)) {
            var P = S({}, i, {
                xAxisName: m ? [m] : void 0,
                yAxisName: p ? [p] : void 0,
                scale: H ? [H] : void 0,
                min: R ? [R] : void 0,
                max: C ? [C] : void 0,
                dimension: r ? [r] : void 0
            }), D = ne(e, t, P, n);
            return D && D.series ? (D.series.forEach(function (e) {
                S(e, {
                    type: "scatter",
                    symbol: T,
                    symbolSize: N || 10,
                    symbolRotate: k,
                    symbolOffset: j,
                    cursor: E,
                    label: F,
                    itemStyle: _
                })
            }), D) : {}
        }
        var q, $ = n.tooltipVisible, G = n.legendVisible, B = Object.keys(t);
        return {
            legend: G && function (e, t) {
                return {
                    data: e, formatter: function (e) {
                        return null != t[e] ? t[e] : e
                    }
                }
            }(B, b),
            tooltip: $ && {
                trigger: (q = {
                    tooltipTrigger: w,
                    labelMap: g,
                    columns: e,
                    dataType: c,
                    digit: f
                }).tooltipTrigger, formatter: function (e) {
                    return L(e) ? e.map(function (e) {
                        return ze(e, q)
                    }).join("") : ze(e, q)
                }
            },
            xAxis: function (e) {
                var t = e.xAxisName, i = e.axisVisible, n = e.xAxisType, a = e.rows, r = e.dataLabels, o = e.dimension,
                    s = [];
                return r.forEach(function (e) {
                    a[e].forEach(function (e) {
                        var t = e[o];
                        t && !~s.indexOf(t) && s.push(t)
                    })
                }), [{type: n, show: i, name: t, data: s}]
            }({xAxisName: m, axisVisible: O, xAxisType: d, dataLabels: B, dimension: r, rows: t}),
            yAxis: function (e) {
                var t = e.min, i = e.max, n = e.scale, a = e.yAxisName, r = e.dataType, o = e.metrics, s = e.digit;
                return {
                    type: "value",
                    show: e.axisVisible,
                    scale: n,
                    min: t,
                    max: i,
                    axisTick: {show: !1},
                    name: a,
                    axisLabel: {
                        formatter: function (e) {
                            return z(e, r[o[0]], s)
                        }
                    }
                }
            }({min: R, max: C, scale: H, yAxisName: p, dataType: c, metrics: s, digit: f, axisVisible: O}),
            series: function (e) {
                var t = e.rows, i = e.dataLabels, n = e.columns, a = e.metrics, r = e.dimension, o = e.label,
                    s = e.itemStyle, l = e.symbol, c = e.symbolSizeMax, u = e.symbolSize, d = e.symbolRotate,
                    m = e.symbolOffset, p = e.cursor, h = n.filter(function (e) {
                        return !~a.indexOf(e) && e !== r
                    }), f = [];
                i.forEach(function (e) {
                    t[e].forEach(function (e) {
                        f.push(e[a[1]])
                    })
                });
                var v = Math.max.apply(null, f), b = [];
                return i.forEach(function (e) {
                    var i = [];
                    t[e].forEach(function (e) {
                        var t = {value: []};
                        t.value.push(e[r], e[a[0]], e[a[1]]), h.forEach(function (i) {
                            t.value.push(e[i])
                        }), t.symbolSize = u || e[a[1]] / v * c, i.push(t)
                    }), b.push({
                        type: "scatter",
                        data: i,
                        name: e,
                        label: o,
                        itemStyle: s,
                        symbol: l,
                        symbolRotate: d,
                        symbolOffset: m,
                        cursor: p
                    })
                }), b
            }({
                rows: t,
                dataLabels: B,
                columns: e,
                metrics: s,
                dimension: r,
                label: F,
                itemStyle: _,
                symbol: T,
                symbolSizeMax: V,
                symbolSize: N,
                symbolRotate: k,
                symbolOffset: j,
                cursor: E
            })
        }
    }, Ce = S({}, ee, {
        name: "VeScatter", data: function () {
            return this.chartHandler = Re, {}
        }
    }), He = [5, 10, 20, 30], Fe = "K", _e = {show: !1};
    var Pe = function (e, t, i, n) {
        var a = i.dimension, o = void 0 === a ? e[0] : a, s = i.metrics, l = void 0 === s ? e.slice(1, 6) : s,
            c = i.digit, u = void 0 === c ? 2 : c, d = i.itemStyle, m = i.labelMap, p = void 0 === m ? {} : m,
            h = i.legendName, f = void 0 === h ? {} : h, v = i.MA, b = void 0 === v ? He : v, y = i.showMA,
            g = void 0 !== y && y, x = i.showVol, w = void 0 !== x && x, S = i.showDataZoom, A = void 0 !== S && S,
            O = i.downColor, M = void 0 === O ? "#ec0000" : O, V = i.upColor, T = void 0 === V ? "#00da3c" : V,
            N = i.start, k = void 0 === N ? 50 : N, j = i.end, E = void 0 === j ? 100 : j, R = i.dataType,
            C = n.tooltipVisible, H = n.legendVisible, F = L(t[0]), _ = [], P = [], D = [], q = l.slice(0, 4), $ = l[4];
        F ? t.forEach(function (t) {
            var i = [];
            _.push(t[e.indexOf(o)]), q.forEach(function (n) {
                i.push(t[e.indexOf(n)])
            }), P.push(i), $ && D.push(t[e.indexOf($)])
        }) : t.forEach(function (e, t) {
            var i = [];
            if (_.push(e[o]), q.forEach(function (t) {
                i.push(e[t])
            }), P.push(i), $) {
                var n = e[l[0]] > e[l[1]] ? 1 : -1;
                D.push([t, e[$], n])
            }
        });
        var G = H && function (e) {
            var t = e.showMA, i = e.MA, n = e.legendName, a = e.labelMap, r = [Fe];
            return t && (r = r.concat(i.map(function (e) {
                return "MA" + e
            }))), a && (r = r.map(function (e) {
                return null == a[e] ? e : a[e]
            })), {
                data: r, formatter: function (e) {
                    return null != n[e] ? n[e] : e
                }
            }
        }({showMA: g, MA: b, legendName: f, labelMap: p}), B = C && function (e) {
            var t = e.metrics, i = e.dataType, n = e.digit, a = e.labelMap;
            return {
                trigger: "axis", axisPointer: {type: "cross"}, position: function (e, t, i, n, a) {
                    var r = {top: 10};
                    return r[e[0] < a.viewSize[0] / 2 ? "right" : "left"] = 60, r
                }, formatter: function (e) {
                    var o = [];
                    return o.push(e[0].axisValue + "<br>"), e.forEach(function (e) {
                        var s = e.data, l = e.seriesName, c = e.componentSubType, u = e.color,
                            d = null == a[l] ? l : a[l];
                        if (o.push(r(u) + " " + d + ": "), "candlestick" === c) o.push("<br>"), t.slice(0, 4).forEach(function (e, t) {
                            var r = null != a[e] ? a[e] : e, l = z(s[t + 1], i, n);
                            o.push("- " + r + ": " + l + "<br>")
                        }); else if ("line" === c) {
                            var m = z(s, i, n);
                            o.push(m + "<br>")
                        } else if ("bar" === c) {
                            var p = z(s[1], i, n);
                            o.push(p + "<br>")
                        }
                    }), o.join("")
                }
            }
        }({metrics: l, dataType: R, digit: u, labelMap: p}), W = w && function (e) {
            var t = e.downColor, i = e.upColor, n = e.MA;
            return {
                show: !1,
                seriesIndex: e.showMA ? 1 + n.length : 1,
                dimension: 2,
                pieces: [{value: 1, color: t}, {value: -1, color: i}]
            }
        }({downColor: M, upColor: T, MA: b, showMA: g}), I = A && function (e) {
            var t = e.start, i = e.end;
            return [{type: "inside", xAxisIndex: [0, 1], start: t, end: i}, {
                show: !0,
                xAxisIndex: [0, 1],
                type: "slider",
                top: "85%",
                start: t,
                end: i
            }]
        }({start: k, end: E});
        return {
            legend: G,
            tooltip: B,
            visualMap: W,
            grid: [{
                left: "10%",
                right: "8%",
                top: "10%",
                height: {showVol: w}.showVol ? "50%" : "65%",
                containLabel: !1
            }, {left: "10%", right: "8%", top: "65%", height: "16%", containLabel: !1}],
            xAxis: function (e) {
                var t = e.dims, i = {onZero: !1};
                return [{
                    type: "category",
                    data: t,
                    scale: !0,
                    boundaryGap: !1,
                    axisLine: i,
                    splitLine: _e,
                    min: "dataMin",
                    max: "dataMax"
                }, {
                    type: "category",
                    gridIndex: 1,
                    data: t,
                    scale: !0,
                    boundaryGap: !1,
                    axisLine: i,
                    axisTick: _e,
                    splitLine: _e,
                    axisLabel: _e,
                    min: "dataMin",
                    max: "dataMax"
                }]
            }({dims: _}),
            yAxis: function (e) {
                var t = e.dataType, i = e.digit;
                return [{
                    scale: !0, axisTick: _e, axisLabel: {
                        formatter: function (e) {
                            return z(e, t, i)
                        }
                    }
                }, {scale: !0, gridIndex: 1, splitNumber: 2, axisLine: _e, axisTick: _e, splitLine: _e, axisLabel: _e}]
            }({dataType: R, digit: u}),
            dataZoom: I,
            series: function (e) {
                var t = e.values, i = e.volumes, n = e.upColor, a = e.downColor, r = e.showMA, o = e.MA, s = e.showVol,
                    l = e.labelMap, c = e.digit,
                    u = e.itemStyle || {normal: {color: n, color0: a, borderColor: null, borderColor0: null}},
                    d = {normal: {opacity: .5}},
                    m = [{name: null == l[Fe] ? Fe : l[Fe], type: "candlestick", data: t, itemStyle: u}];
                return r && o.forEach(function (e) {
                    var i = "MA" + e;
                    m.push({
                        name: null == l[i] ? i : l[i], data: function (e, t, i) {
                            var n = [];
                            return t.forEach(function (a, r) {
                                if (r < e) n.push("-"); else {
                                    for (var o = 0, s = 0; s < e; s++) o += t[r - s][1];
                                    n.push(+(o / e).toFixed(i))
                                }
                            }), n
                        }(e, t, c), type: "line", lineStyle: d, smooth: !0
                    })
                }), s && m.push({name: "Volume", type: "bar", xAxisIndex: 1, yAxisIndex: 1, data: i}), m
            }({
                values: P,
                volumes: D,
                upColor: T,
                downColor: M,
                showMA: g,
                MA: b,
                showVol: w,
                labelMap: p,
                digit: u,
                itemStyle: d
            }),
            axisPointer: {link: {xAxisIndex: "all"}}
        }
    }, De = S({}, ee, {
        name: "VeCandle", data: function () {
            return this.chartHandler = Pe, {}
        }
    });
    var qe = function (e, t, i, n) {
        var a = i.dimension, r = void 0 === a ? e[0] : a, o = i.metrics, s = void 0 === o ? e[1] : o, l = i.digit,
            c = void 0 === l ? 2 : l, u = i.dataType, d = void 0 === u ? {} : u, m = i.labelMap,
            p = void 0 === m ? {} : m, h = i.seriesMap, f = void 0 === h ? {} : h, v = i.dataName,
            b = void 0 === v ? {} : v, y = n.tooltipFormatter;
        return {
            tooltip: n.tooltipVisible && function (e) {
                var t = e.tooltipFormatter, i = e.dataType, n = e.digit;
                return {
                    formatter: function (e) {
                        var a = e.seriesName, r = e.data, o = r.value, s = r.name;
                        if (t) return t.apply(null, arguments);
                        var l = [];
                        return l.push(a + ": "), l.push(z(o, i[a], n) + " " + s), l.join("")
                    }
                }
            }({tooltipFormatter: y, dataType: d}), series: function (e) {
                var t = e.rows, i = e.dimension, n = e.metrics, a = e.digit, r = e.dataType, o = e.labelMap,
                    s = e.seriesMap, l = e.dataName;
                return t.map(function (e) {
                    var t = e[i], c = s[t], u = {
                        type: "gauge",
                        name: null != o[t] ? o[t] : t,
                        data: [{name: l[t] || "", value: e[n]}],
                        detail: {
                            formatter: function (e) {
                                return z(e, r[t], a)
                            }
                        },
                        axisLabel: {
                            formatter: function (e) {
                                return z(e, r[t], a)
                            }
                        }
                    };
                    return c && Object.keys(c).forEach(function (e) {
                        k(u[e]) ? S(u[e], c[e]) : u[e] = c[e]
                    }), u
                })
            }({rows: t, dimension: r, metrics: s, digit: c, dataType: d, labelMap: p, seriesMap: f, dataName: b})
        }
    }, $e = S({}, ee, {
        name: "VeGauge", data: function () {
            return this.chartHandler = qe, {}
        }
    });
    var Ge = function (e, t, i, n) {
        var a = i.dimension, r = void 0 === a ? e[0] : a, o = i.metrics, s = void 0 === o ? e[1] : o, l = i.seriesMap,
            c = void 0 === l ? {} : l, u = n.legendVisible, d = n.tooltipFormatter, m = n.tooltipVisible;
        return {
            series: function (e) {
                var t = e.dimension, i = e.metrics, n = e.rows, a = e.seriesMap, r = [];
                return n.forEach(function (e) {
                    var n = e[t], o = a[n], s = {type: "tree", name: e[t], data: e[i]};
                    a[e[t]] && Object.keys(o).forEach(function (e) {
                        k(s[e]) ? S(s[e], o[e]) : s[e] = o[e]
                    }), r.push(s)
                }), r
            }({dimension: r, metrics: s, rows: t, seriesMap: c}),
            legend: u && t.length > 1 && function (e) {
                var t = e.dimension;
                return {
                    data: e.rows.map(function (e) {
                        return e[t]
                    })
                }
            }({dimension: r, rows: t}),
            tooltip: m && {trigger: "item", triggerOn: "mousemove", formatter: {tooltipFormatter: d}.tooltipFormatter}
        }
    }, Be = S({}, ee, {
        name: "VeTree", data: function () {
            return this.chartHandler = Ge, {}
        }
    });
    var We = function (e, t, i, n) {
        var a = i.dimension, r = void 0 === a ? e[0] : a, o = i.metrics, s = void 0 === o ? e[1] : o, l = i.seriesMap,
            c = void 0 === l ? {} : l, u = i.dataType, d = void 0 === u ? "percent" : u, m = i.digit,
            p = void 0 === m ? 2 : m, h = i.wave, f = void 0 === h ? [] : h, v = n.tooltipVisible,
            b = n.tooltipFormatter;
        return {
            tooltip: v && function (e) {
                var t = e.tooltipFormatter, i = e.dataType, n = e.digit;
                return {
                    show: !0, formatter: function (e) {
                        var a = e.seriesName, r = e.value;
                        return t ? t.apply(null, arguments) : [a + ": ", z(r, i, n)].join("")
                    }
                }
            }({tooltipFormatter: b, dataType: d, digit: p}), series: function (e) {
                var t = e.dimension, i = e.metrics, n = e.seriesMap, a = e.rows, r = e.wave, o = r,
                    s = L(n) ? n.length : 0;
                return a.slice().map(function (e, a) {
                    var l = [], c = {type: "liquidFill"}, u = e[t], d = Number(e[i]), m = {};
                    return L(n) ? m = n[a] ? n[a] : n[s - 1] : k(n[u]) && (m = n[u]), L(r) && L(r[0]) && (o = L(r[a]) ? r[a] : r[r.length - 1]), l.push({value: d}), o && o.length && (l = l.concat(o.map(function (e) {
                        return {value: e}
                    }))), c = S(c, {data: l, name: u}, m)
                })
            }({rows: t, columns: e, dimension: r, metrics: s, seriesMap: c, wave: f})
        }
    }, Ie = S({}, ee, {
        name: "VeLiquidfill", data: function () {
            return this.chartHandler = We, {}
        }
    });
    var Ze = function (e, t, i, n) {
        var a = i.dimension, r = void 0 === a ? e[0] : a, o = i.metrics, s = void 0 === o ? e[1] : o, l = i.color,
            c = void 0 === l ? "" : l, u = i.sizeMax, d = void 0 === u ? 60 : u, m = i.sizeMin,
            p = void 0 === m ? 12 : m, h = i.shape, f = void 0 === h ? "circle" : h, v = n.tooltipVisible,
            b = n.tooltipFormatter;
        return {
            series: function (e) {
                var t = e.dimension, i = e.metrics, n = e.rows, a = e.color, r = e.sizeMax, o = e.sizeMin, s = e.shape,
                    l = {
                        type: "wordCloud", textStyle: {
                            normal: {
                                color: !L(a) && a ? a : function () {
                                    return "rgb(" + [Math.round(160 * Math.random()), Math.round(160 * Math.random()), Math.round(160 * Math.random())].join(",") + ")"
                                }
                            }
                        }, shape: s, sizeRange: [o, r]
                    }, c = L(a) ? a.length : 0, u = n.slice().map(function (e) {
                        var n = {name: e[t], value: e[i]};
                        return c > 0 && (n.textStyle = {normal: {color: a[Math.floor(Math.random() * c)]}}), n
                    });
                return l.data = u, [l]
            }({dimension: r, metrics: s, rows: t, color: c, sizeMax: d, sizeMin: p, shape: f}),
            tooltip: v && function (e) {
                var t = e.tooltipFormatter;
                return {
                    show: !0, formatter: function (e) {
                        var i = e.data, n = i.name, a = i.value;
                        return t ? t.apply(null, e) : n + ": " + a
                    }
                }
            }({tooltipFormatter: b})
        }
    }, Je = S({}, ee, {
        name: "VeWordcloud", data: function () {
            return this.chartHandler = Ze, {}
        }
    }), Ue = [te, ie, ae, ce, ue, me, he, ve, be, we, Ae, Me, Te, Ee, Ce, De, $e, Be, Ie, Je];

    function Ye(e, t) {
        Ue.forEach(function (t) {
            e.component(t.name, t)
        })
    }

    return "undefined" != typeof window && window.Vue && Ye(window.Vue), {
        VeBar: te,
        VeHistogram: ie,
        VeRing: ue,
        VeLine: ae,
        VePie: ce,
        VeWaterfall: me,
        VeFunnel: he,
        VeRadar: ve,
        VeChart: be,
        VeMap: we,
        VeBmap: Ae,
        VeAmap: Me,
        VeSankey: Te,
        VeScatter: Ce,
        VeCandle: De,
        VeGauge: $e,
        VeTree: Be,
        VeLiquidfill: Ie,
        VeWordcloud: Je,
        install: Ye
    }
});
