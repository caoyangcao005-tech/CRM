(function () {
    "use strict";

    var BASE = "../api/customer-services";

    function escapeHtml(value) {
        return String(value == null ? "" : value)
            .replace(/&/g, "&amp;").replace(/</g, "&lt;")
            .replace(/>/g, "&gt;").replace(/"/g, "&quot;")
            .replace(/'/g, "&#39;");
    }

    async function request(path, options) {
        var config = options || {};
        config.headers = Object.assign({"Content-Type": "application/json"}, config.headers || {});
        var response = await fetch(BASE + (path || ""), config);
        var payload;
        try {
            payload = await response.json();
        } catch (ignore) {
            throw new Error("服务器返回了无法识别的数据");
        }
        if (!response.ok || !payload.success) {
            throw new Error(payload.message || "操作失败");
        }
        return payload;
    }

    function params(values) {
        var query = new URLSearchParams();
        Object.keys(values).forEach(function (key) {
            var value = values[key];
            if (value !== undefined && value !== null && value !== "" && value !== "全部") {
                query.set(key, value);
            }
        });
        return query.toString();
    }

    function message(text, success) {
        var box = document.getElementById("message");
        if (!box) {
            alert(text);
            return;
        }
        box.textContent = text;
        box.className = "message show " + (success ? "success" : "error");
        window.clearTimeout(message.timer);
        message.timer = window.setTimeout(function () { box.className = "message"; }, 4500);
    }

    function value(id) {
        var element = document.getElementById(id);
        return element ? element.value.trim() : "";
    }

    function setOptions(select, values, label, getValue, getText) {
        select.innerHTML = "";
        if (label !== null && label !== undefined) {
            var first = document.createElement("option");
            first.value = "";
            first.textContent = label;
            select.appendChild(first);
        }
        (values || []).forEach(function (item) {
            var option = document.createElement("option");
            option.value = getValue ? getValue(item) : item;
            option.textContent = getText ? getText(item) : item;
            select.appendChild(option);
        });
    }

    function formatDate(value) {
        return value ? value.replace("T", " ") : "-";
    }

    function queryId() {
        var raw = new URLSearchParams(window.location.search).get("id");
        var id = Number(raw);
        return Number.isInteger(id) && id > 0 ? id : null;
    }

    function renderPager(target, page, onChange) {
        target.innerHTML = "";
        var info = document.createElement("span");
        info.textContent = "共 " + page.total + " 条，第 " + (page.totalPages ? page.page : 0) + "/" + page.totalPages + " 页";
        var buttons = document.createElement("div");
        buttons.className = "pager-buttons";
        [["上一页", page.page - 1, page.page <= 1], ["下一页", page.page + 1, page.page >= page.totalPages]]
            .forEach(function (item) {
                var button = document.createElement("button");
                button.type = "button";
                button.textContent = item[0];
                button.disabled = item[2];
                button.onclick = function () { onChange(item[1]); };
                buttons.appendChild(button);
            });
        target.appendChild(info);
        target.appendChild(buttons);
    }

    function detailHtml(s) {
        function row(label1, value1, label2, value2) {
            return '<div class="term">' + label1 + '</div><div>' + escapeHtml(value1 || "-") +
                '</div><div class="term">' + label2 + '</div><div>' + escapeHtml(value2 || "-") + '</div>';
        }
        function wide(label, value) {
            return '<div class="term">' + label + '</div><div class="wide">' + escapeHtml(value || "-") + '</div>';
        }
        return row("服务编号", s.svrId, "当前状态", s.svrStatus) +
            row("客户名称", s.svrCustName, "服务类型", s.svrType) +
            wide("服务概要", s.svrTitle) + wide("服务请求", s.svrRequest) +
            row("创建人", s.svrCreateBy, "创建时间", formatDate(s.svrCreateDate)) +
            row("分配给", s.svrDueTo, "分配时间", formatDate(s.svrDueDate)) +
            wide("处理方法", s.svrDeal) +
            row("处理人", s.svrDealBy, "处理时间", formatDate(s.svrDealDate)) +
            wide("处理结果", s.svrResult) +
            row("满意度", s.svrSatisfy ? s.svrSatisfy + " 分" : "-", "归档状态", s.svrStatus);
    }

    window.ServiceApp = {
        api: request,
        params: params,
        value: value,
        esc: escapeHtml,
        message: message,
        setOptions: setOptions,
        formatDate: formatDate,
        queryId: queryId,
        renderPager: renderPager,
        detailHtml: detailHtml
    };
}());
