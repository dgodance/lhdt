
var KTDatatableRemoteAjaxDemo = function() {
    // Private functions

    // basic demo
    var demo = function() {

        var datatable = $('.kt-datatable').KTDatatable({
            // datasource definition
            data: {
                type: 'remote',
                source: {
                    read: {
                        url: 'http://localhost:9091/adminsvc/ls-anals-rest',
                        map: function(raw) {
                            // sample data mapping
                            var dataSet = raw;
                            if (typeof raw.data !== 'undefined') {
                                dataSet = raw.data;
                            }
                            return dataSet;
                        },
                    },
                },
                pageSize: 10
            },

            // layout definition
            layout: {
                scroll: true,
                footer: false,
            },

            // column sorting
            sortable: true,

            pagination: true,

            search: {
                input: $('#generalSearch'),
            },
            // columns definition
            columns: [
                {
                    field: 'id',
                    title: '아이디',
                    selector: false,
                    textAlign: 'center',
                },
                {
                    field: 'lsDiffGrupName',
                    title: '그룹명',
                    selector: false,
                    textAlign: 'center',
                },
                {
                    field: 'registDt',
                    title: '등록일',
                    selector: false,
                    textAlign: 'center',
                    type: 'date',
                    format: 'YYYY/MM/DD',
                },
                {
                    field: 'updtDt',
                    title: '수정일',
                    selector: false,
                    textAlign: 'center',
                    type: 'date',
                    format: 'YYYY/MM/DD',
                },
                {
                    field: "modifiedAction",
                    width: 80,
                    title: "수정",
                    sortable: false,
                    autoHide: false,
                    overflow: 'visible',
                    textAlign: 'center',
                    template: function (row) {
                        debugger;
                        return '' +
                            "<span style='overflow: visible; position: relative; width: 80px;'>"+
                            "<button class=\"btn btn-bold btn-label-brand btn-sm\" " +
                            "onclick=\"'lsDiffModiBtn("+row.id + "," + row.lsDiffGrupName+")'\""+
                            "data-toggle=\"modal\" data-target=\"#ls-diff-modi-modal\">수정</button>"+
                            "</span>"
                    },

                },
                {
                    field: "deleteAction",
                    width: 80,
                    title: "삭제",
                    sortable: false,
                    autoHide: false,
                    overflow: 'visible',
                    textAlign: 'center',
                    template: function (row) {
                        debugger;
                        return '' +
                            "<form method='POST'"+
                            "action='/adminsvc/ls-diff-group/delete/"+row.id+"'"+
                            "onsubmit='return confirm('정말 삭제하시겠습니까?');'>"+
                            "<span style='overflow: visible; position: relative; width: 80px;'>"+
                            "<button class='btn btn-bold btn-sm btn-font-sm  btn-label-success'>삭제</button>"+
                            "</span>"+
                            "</form>";
                    },
                }],

        });

        $('#kt_form_status').on('change', function() {
            datatable.search($(this).val().toLowerCase(), 'Status');
        });

        $('#kt_form_type').on('change', function() {
            datatable.search($(this).val().toLowerCase(), 'Type');
        });

        $('#kt_form_status,#kt_form_type').selectpicker();

    };

    return {
        // public functions
        init: function() {
            demo();
        },
    };
}();

function lsDiffModiBtn(id, value) {
    $('#lsDiffGrupName').val('');

    const action = window.location.href + '/edit/' + id;
    $('#ls-diff-modi-modal-form').attr('action', action);
    $('#lsDiffGrupName').val(value);
}

$(document).ready(function() {
    KTDatatableRemoteAjaxDemo.init();
});